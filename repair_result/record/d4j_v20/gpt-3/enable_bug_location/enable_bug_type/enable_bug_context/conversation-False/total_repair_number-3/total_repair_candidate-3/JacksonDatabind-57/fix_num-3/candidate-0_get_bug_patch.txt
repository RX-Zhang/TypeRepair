public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        return _detectBindAndReadValues(_dataFormatReaders.findFormat(src, offset, length), false);
    }
    // Add boundary checks and exception handling to prevent invalid UTF-32 char errors
    if (src == null) {
        throw new IllegalArgumentException("Source byte array is null");
    }
    if (offset < 0 || length < 0 || offset + length > src.length) {
        throw new IllegalArgumentException("Invalid offset and/or length parameters");
    }
    try {
        return _bindAndReadValues(_considerFilter(_parserFactory.createParser(src, offset, length), true));
    } catch (CharConversionException e) {
        // wrap as IOException to match throws clauses
        throw new IOException("Failed to parse input due to invalid character encoding", e);
    }
}
