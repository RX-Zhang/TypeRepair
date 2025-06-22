public int read(byte[] buffer, int start, int length) throws IOException {
    if (closed) {
        throw new IOException("The stream is closed");
    }
    if (current == null) {
        return -1;
    }

    // avoid int overflow, check null buffer
    if (start < 0 || length < 0 || start > buffer.length - length) {
        throw new ArrayIndexOutOfBoundsException();
    }

    if (current.getMethod() == ZipArchiveOutputStream.STORED) {
        int csize = (int) current.getSize();
        if (readBytesOfEntry >= csize) {
            // no more data
            return -1;
        }
        if (offsetInBuffer >= lengthOfLastRead) {
            offsetInBuffer = 0;
            if ((lengthOfLastRead = in.read(buf)) == -1) {
                // truncated entry
                throw new IOException("Truncated ZIP file");
            }
            count(lengthOfLastRead);
            bytesReadFromStream += lengthOfLastRead;
        }
        int toRead = Math.min(length, lengthOfLastRead - offsetInBuffer);
        if (csize - readBytesOfEntry < toRead) {
            toRead = csize - readBytesOfEntry;
        }
        System.arraycopy(buf, offsetInBuffer, buffer, start, toRead);
        offsetInBuffer += toRead;
        readBytesOfEntry += toRead;
        crc.update(buffer, start, toRead);
        return toRead;
    } else if (current.getMethod() == ZipArchiveOutputStream.DEFLATED) {
        if (inf.finished()) {
            return -1;
        }
        if (inf.needsInput()) {
            fill();
            if (lengthOfLastRead == -1) {
                throw new IOException("Truncated ZIP file");
            }
            if (lengthOfLastRead > 0) {
                bytesReadFromStream += lengthOfLastRead;
            }
        }
        int read = 0;
        try {
            read = inf.inflate(buffer, start, length);
        } catch (DataFormatException e) {
            throw new ZipException(e.getMessage());
        }
        if (read == 0) {
            if (inf.finished()) {
                return -1;
            }
            if (inf.needsInput()) {
                // refill input buffer again to detect truncated
                fill();
                if (lengthOfLastRead == -1) {
                    throw new IOException("Truncated ZIP file");
                }
                if (lengthOfLastRead > 0) {
                    bytesReadFromStream += lengthOfLastRead;
                }
                try {
                    read = inf.inflate(buffer, start, length);
                } catch (DataFormatException e) {
                    throw new ZipException(e.getMessage());
                }
                if (read == 0 && inf.finished()) {
                    return -1;
                }
            }
            if (read == 0) {
                throw new IOException("Truncated ZIP file");
            }
        }
        crc.update(buffer, start, read);
        readBytesOfEntry += read;
        return read;
    } else {
        throw new IOException("Unsupported compression method " + current.getMethod());
    }
}
