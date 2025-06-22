public int read(byte[] buffer, int start, int length) throws IOException {
    if (closed) {
        throw new IOException("The stream is closed");
    }
    if (current == null) {
        return -1;
    }

    // avoid int overflow, check null buffer, and check start and length bounds
    if (buffer == null || start < 0 || length < 0 || start > buffer.length - length) {
        throw new ArrayIndexOutOfBoundsException();
    }

    if (current.getMethod() == ZipArchiveOutputStream.STORED) {
        int csize = (int) current.getSize();
        if (readBytesOfEntry >= csize) {
            // We reached end of stored entry
            return -1;
        }
        if (offsetInBuffer >= lengthOfLastRead) {
            offsetInBuffer = 0;
            if ((lengthOfLastRead = in.read(buf)) == -1) {
                throw new IOException("Truncated ZIP file");
            }
            count(lengthOfLastRead);
            bytesReadFromStream += lengthOfLastRead;
        }
        int toRead = length;
        int remainingInBuf = lengthOfLastRead - offsetInBuffer;
        if (toRead > remainingInBuf) {
            toRead = remainingInBuf;
        }
        int remainingInEntry = csize - readBytesOfEntry;
        if (toRead > remainingInEntry) {
            toRead = remainingInEntry;
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
        int read;
        try {
            read = inf.inflate(buffer, start, length);
        } catch (DataFormatException e) {
            throw new ZipException(e.getMessage());
        }
        if (read == 0) {
            if (inf.finished()) {
                return -1;
            }
            // If no bytes could be read and not finished, try to fill again once more
            if (inf.needsInput()) {
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
                // This means no progress could be made, treat as truncated
                throw new IOException("Truncated ZIP file");
            }
        }
        crc.update(buffer, start, read);
        readBytesOfEntry += read;
        return read;
    } else {
        throw new ZipException("Unsupported compression method " + current.getMethod());
    }
}
