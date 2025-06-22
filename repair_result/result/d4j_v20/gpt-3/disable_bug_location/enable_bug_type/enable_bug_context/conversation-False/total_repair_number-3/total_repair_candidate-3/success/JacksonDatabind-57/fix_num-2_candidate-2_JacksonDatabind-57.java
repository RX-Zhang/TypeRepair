public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        return _detectBindAndReadValues(_dataFormatReaders.findFormat(src, offset, length), false);
    }
    try {
        // Adding boundary check for offset and length to avoid runtime exceptions
        if (src == null) {
            throw new IllegalArgumentException("Input byte array is null");
        }
        if (offset < 0 || length < 0 || (offset + length) > src.length) {
            throw new IllegalArgumentException("Offset and length out of bounds");
        }
        return _bindAndReadValues(_considerFilter(_parserFactory.createParser(src, offset, length), true));
    } catch (IOException e) {
        // Optionally log or rethrow after wrapping, but here just rethrow
        throw e;
    }
}
