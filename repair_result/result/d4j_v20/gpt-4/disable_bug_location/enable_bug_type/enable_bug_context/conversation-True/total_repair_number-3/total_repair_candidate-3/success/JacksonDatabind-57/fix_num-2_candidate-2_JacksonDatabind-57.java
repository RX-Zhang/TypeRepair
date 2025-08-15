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
    // Validate offset and length to avoid out-of-bounds or invalid UTF-32 chars issues
    if (src == null) {
        throw new IllegalArgumentException("Source byte array is null");
    }
    if (offset < 0 || length < 0 || offset + length > src.length) {
        throw new IllegalArgumentException("Invalid offset and/or length for the source byte array");
    }
    return _bindAndReadValues(_considerFilter(_parserFactory.createParser(src, offset, length), true));
}
