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
    try {
        JsonParser parser = _parserFactory.createParser(src, offset, length);
        parser = _considerFilter(parser, true);
        return _bindAndReadValues(parser);
    } catch (CharConversionException e) {
        // wrap or rethrow if necessary, or handle gracefully
        throw e;
    }
}
