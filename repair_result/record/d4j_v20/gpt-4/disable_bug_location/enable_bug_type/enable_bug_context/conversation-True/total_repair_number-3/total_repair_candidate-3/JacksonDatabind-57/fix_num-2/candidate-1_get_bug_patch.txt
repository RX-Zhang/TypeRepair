public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        DataFormatReaders.Match match = _dataFormatReaders.findFormat(src, offset, length);
        if (!match.hasMatch()) {
            _reportUnkownFormat(_dataFormatReaders, match);
        }
        return _detectBindAndReadValues(match, false);
    }
    // Boundary check for offset and length
    if (src == null || offset < 0 || length < 0 || offset + length > src.length) {
        throw new IllegalArgumentException("Invalid offset/length parameters for input byte array");
    }
    JsonParser parser = _parserFactory.createParser(src, offset, length);
    return _bindAndReadValues(_considerFilter(parser, true));
}
