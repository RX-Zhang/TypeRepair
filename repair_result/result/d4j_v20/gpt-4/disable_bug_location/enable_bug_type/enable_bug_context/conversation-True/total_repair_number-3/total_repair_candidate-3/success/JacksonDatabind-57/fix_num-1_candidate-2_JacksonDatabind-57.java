public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        DataFormatReaders.Match match = _dataFormatReaders.findFormat(src, offset, length);
        if (!match.hasMatch()) {
            // Throw or handle unknown format
            _reportUnkownFormat(_dataFormatReaders, match);
        }
        try {
            return _detectBindAndReadValues(match, false);
        } catch (CharConversionException e) {
            // Handle CharConversionException gracefully or rethrow with informative message
            throw new IOException("Invalid UTF-32 character detected during format detection", e);
        }
    }
    if (offset < 0 || length < 0 || offset + length > src.length) {
        throw new IllegalArgumentException("Invalid offset and length parameters");
    }
    JsonParser parser = null;
    try {
        parser = _parserFactory.createParser(src, offset, length);
        parser = _considerFilter(parser, true);
        return _bindAndReadValues(parser);
    } catch (CharConversionException e) {
        throw new IOException("Invalid UTF-32 character detected during parsing", e);
    }
}
