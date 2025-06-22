public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        return _detectBindAndReadValues(_dataFormatReaders.findFormat(src, offset, length), false);
    }
    if (offset < 0 || length < 0 || offset + length > src.length) {
        throw new IllegalArgumentException("Invalid offset or length for input byte array");
    }
    return _bindAndReadValues(_considerFilter(_parserFactory.createParser(src, offset, length), 
            true));
}
