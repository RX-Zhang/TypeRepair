public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        DataFormatReaders.Match match = _dataFormatReaders.findFormat(src, offset, length);
        return _detectBindAndReadValues(match, false);
    }
    JsonParser p = _parserFactory.createParser(src, offset, length);
    return _bindAndReadValues(_considerFilter(p, true));
}
