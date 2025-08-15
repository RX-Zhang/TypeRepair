public int read(byte[] buffer, int start, int length) throws IOException {
    if (closed) {
        throw new IOException("The stream is closed");
    }
    if (current == null) {
        return -1;
    }

    // avoid int overflow, check null buffer and valid ranges
    if (buffer == null || start < 0 || length < 0 || start > buffer.length - length) {
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
        int toRead = length;
        if (toRead > lengthOfLastRead - offsetInBuffer) {
            toRead = lengthOfLastRead - offsetInBuffer;
        }
        if (toRead > csize - readBytesOfEntry) {
            toRead = csize - readBytesOfEntry;
        }
        if (toRead <= 0) {
            return -1;
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
        if (lengthOfLastRead == -1 || inf.needsInput()) {
            // truncated entry (no more input but inflate not finished)
            throw new IOException("Truncated ZIP file");
        }
    }

    crc.update(buffer, start, read);
    readBytesOfEntry += read;
    return read;
}
