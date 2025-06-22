public int read(byte[] buffer, int start, int length) throws IOException {
    if (closed) {
        throw new IOException("The stream is closed");
    }
    if (current == null) {
        return -1;
    }
    if (start < 0 || length < 0 || start > buffer.length - length) {
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
                // truncated entry
                throw new IOException("Truncated ZIP file");
            }
            count(lengthOfLastRead);
            bytesReadFromStream += lengthOfLastRead;
        }
        int toRead = length > lengthOfLastRead - offsetInBuffer
            ? lengthOfLastRead - offsetInBuffer
            : length;
        if ((csize - readBytesOfEntry) < toRead) {
            toRead = csize - readBytesOfEntry;
        }
        System.arraycopy(buf, offsetInBuffer, buffer, start, toRead);
        offsetInBuffer += toRead;
        readBytesOfEntry += toRead;
        crc.update(buffer, start, toRead);
        return toRead;
    }

    if (inf.finished()) {
        return -1;
    }

    if (inf.needsInput()) {
        fill();
        if (lengthOfLastRead == -1) {
            // truncated entry
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
        // this means no output but input was available, possibly truncated
        throw new IOException("Truncated ZIP file");
    }
    crc.update(buffer, start, read);
    readBytesOfEntry += read;
    return read;
}
