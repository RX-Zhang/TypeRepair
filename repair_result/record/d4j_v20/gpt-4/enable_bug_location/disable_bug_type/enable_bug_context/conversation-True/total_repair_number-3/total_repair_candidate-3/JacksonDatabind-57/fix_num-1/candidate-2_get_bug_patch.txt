public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        return _detectBindAndReadValues(_dataFormatReaders.findFormat(src, offset, length), true);
    }
    JsonParser p = _parserFactory.createParser(src, offset, length);
    return _bindAndReadValues(_considerFilter(p, true));
}
