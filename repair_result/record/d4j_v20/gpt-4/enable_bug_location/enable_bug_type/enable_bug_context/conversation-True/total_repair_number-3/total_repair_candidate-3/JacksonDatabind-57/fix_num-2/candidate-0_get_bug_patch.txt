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

    if (src == null) {
        throw new IllegalArgumentException("source byte array cannot be null");
    }
    if (offset < 0 || length < 0 || offset + length > src.length) {
        throw new IllegalArgumentException("Invalid offset and length parameters");
    }

    JsonParser parser = _parserFactory.createParser(src, offset, length);
    parser = _considerFilter(parser, true);
    return _bindAndReadValues(parser);
}
