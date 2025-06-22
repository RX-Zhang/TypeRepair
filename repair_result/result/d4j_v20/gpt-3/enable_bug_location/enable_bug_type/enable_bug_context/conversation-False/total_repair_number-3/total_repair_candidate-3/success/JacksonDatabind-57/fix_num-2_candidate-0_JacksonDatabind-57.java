public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        // Add boundary check to ensure offset and length are valid
        if (offset < 0 || length < 0 || offset + length > src.length) {
            throw new IllegalArgumentException("Invalid offset and/or length for input byte array");
        }
        return _detectBindAndReadValues(_dataFormatReaders.findFormat(src, offset, length), false);
    }
    try {
        return _bindAndReadValues(_considerFilter(_parserFactory.createParser(src, offset, length), 
                true));
    } catch (CharConversionException e) {
        // Wrap or rethrow with useful message or handle gracefully
        throw new IOException("Invalid character encoding in input byte array", e);
    }
}
