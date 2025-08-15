public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        DataFormatReaders.Match match = _dataFormatReaders.findFormat(src, offset, length);
        // Validate that match is valid before proceeding
        if (!match.hasMatch()) {
            _reportUnkownFormat(_dataFormatReaders, match);
        }
        return _detectBindAndReadValues(match, false);
    }
    // Validate input parameters for parser creation
    if (src == null || offset < 0 || length < 0 || offset + length > src.length) {
        throw new IllegalArgumentException("Invalid input byte array, offset, or length");
    }
    JsonParser p = _parserFactory.createParser(src, offset, length);
    p = _considerFilter(p, true);
    return _bindAndReadValues(p);
}
