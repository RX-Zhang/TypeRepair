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
        return _bindAndReadValues(_considerFilter(_parserFactory.createParser(src, offset, length), 
                true));
    } catch (CharConversionException e) {
        // Wrap or rethrow with additional context if needed, or just rethrow
        throw e;
    }
}
