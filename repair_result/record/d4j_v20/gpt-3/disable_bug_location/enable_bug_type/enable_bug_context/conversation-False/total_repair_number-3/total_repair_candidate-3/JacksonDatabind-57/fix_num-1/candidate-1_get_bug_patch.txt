public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (src == null) {
        throw new IllegalArgumentException("Source byte array is null");
    }
    if (offset < 0 || length < 0 || offset + length > src.length) {
        throw new IndexOutOfBoundsException("Invalid offset and/or length");
    }

    if (_dataFormatReaders != null) {
        return _detectBindAndReadValues(_dataFormatReaders.findFormat(src, offset, length), false);
    }
    return _bindAndReadValues(_considerFilter(_parserFactory.createParser(src, offset, length), 
            true));
}
