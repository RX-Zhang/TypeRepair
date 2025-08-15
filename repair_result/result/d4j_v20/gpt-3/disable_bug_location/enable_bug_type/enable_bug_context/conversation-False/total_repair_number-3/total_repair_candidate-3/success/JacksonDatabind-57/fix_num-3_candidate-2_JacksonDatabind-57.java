public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        DataFormatReaders.Match match = _dataFormatReaders.findFormat(src, offset, length);
        if (!match.hasMatch()) {
            // Instead of failing silently or causing an execution error,
            // throw a descriptive exception to handle unknown format
            _reportUnkownFormat(_dataFormatReaders, match);
        }
        try {
            return _detectBindAndReadValues(match, false);
        } catch (CharConversionException e) {
            // Wrap or rethrow with more context or handle gracefully
            throw new IOException("Invalid character encoding detected in input data", e);
        }
    }
    try {
        return _bindAndReadValues(_considerFilter(_parserFactory.createParser(src, offset, length), 
            true));
    } catch (CharConversionException e) {
        throw new IOException("Invalid character encoding detected in input data", e);
    }
}
