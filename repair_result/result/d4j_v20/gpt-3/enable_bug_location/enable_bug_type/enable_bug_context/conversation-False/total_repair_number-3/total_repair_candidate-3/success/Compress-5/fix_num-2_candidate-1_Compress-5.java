public int read(byte[] buffer, int start, int length) throws IOException {
    if (closed) {
        throw new IOException("The stream is closed");
    }
    if (current == null) {
        return -1;
    }

    // avoid int overflow, check null buffer
    if (!(start >= 0 && length >= 0 && start <= buffer.length && buffer.length - start >= length)) {
        throw new ArrayIndexOutOfBoundsException();
    }

    if (current.getMethod() == ZipArchiveOutputStream.STORED) {
        int csize = (int) current.getSize();
        if (readBytesOfEntry >= csize) {
            return -1;
        }
        if (offsetInBuffer >= lengthOfLastRead) {
            offsetInBuffer = 0;
            lengthOfLastRead = in.read(buf);
            if (lengthOfLastRead == -1) {
                throw new IOException("Truncated ZIP file");
            }
            count(lengthOfLastRead);
            bytesReadFromStream += lengthOfLastRead;
        }
        int toRead = Math.min(length, lengthOfLastRead - offsetInBuffer);
        if ((csize - readBytesOfEntry) < toRead) {
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
                // End of input stream unexpectedly
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
            if (inf.needsInput()) {
                // Could not decompress further and no input, truncated entry
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
