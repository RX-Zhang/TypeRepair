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
    // Validate boundaries to prevent CharConversionException or other errors
    if (src == null || offset < 0 || length < 0 || offset + length > src.length) {
        throw new IllegalArgumentException("Invalid offset and/or length for input byte array");
    }
    JsonParser parser = _parserFactory.createParser(src, offset, length);
    parser = _considerFilter(parser, true);
    return _bindAndReadValues(parser);
}
