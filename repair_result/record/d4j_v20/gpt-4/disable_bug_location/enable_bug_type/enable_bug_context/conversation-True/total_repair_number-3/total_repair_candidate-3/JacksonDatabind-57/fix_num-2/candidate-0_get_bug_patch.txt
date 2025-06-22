public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (src == null) {
        throw new IllegalArgumentException("Input byte array is null");
    }
    if (offset < 0 || length < 0 || offset + length > src.length) {
        throw new IllegalArgumentException("Invalid offset or length: offset="
            + offset + ", length=" + length + ", byte array length=" + src.length);
    }
    if (_dataFormatReaders != null) {
        DataFormatReaders.Match match = _dataFormatReaders.findFormat(src, offset, length);
        if (!match.hasMatch()) {
            _reportUnkownFormat(_dataFormatReaders, match);
        }
        return _detectBindAndReadValues(match, false);
    }
    JsonParser parser = _parserFactory.createParser(src, offset, length);
    try {
        return _bindAndReadValues(_considerFilter(parser, true));
    } catch (IOException e) {
        try {
            parser.close();
        } catch (IOException ioe) {
            // ignore secondary exception
        }
        throw e;
    }
}
