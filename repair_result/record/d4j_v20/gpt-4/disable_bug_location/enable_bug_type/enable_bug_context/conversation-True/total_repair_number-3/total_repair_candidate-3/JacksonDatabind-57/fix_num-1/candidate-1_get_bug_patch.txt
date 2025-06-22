public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        DataFormatReaders.Match match = _dataFormatReaders.findFormat(src, offset, length);
        if (!match.hasMatch()) {
            throw new JsonProcessingException("No matching data format found for input data") {};
        }
        return _detectBindAndReadValues(match, false);
    }
    // Added checks for null and bounds before creating parser
    if (src == null) {
        throw new IllegalArgumentException("Input byte array 'src' must not be null");
    }
    if (offset < 0 || length < 0 || offset + length > src.length) {
        throw new IllegalArgumentException("Invalid offset or length for input byte array");
    }
    return _bindAndReadValues(_considerFilter(_parserFactory.createParser(src, offset, length), 
            true));
}
