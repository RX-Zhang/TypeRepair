public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        try {
            return _detectBindAndReadValues(_dataFormatReaders.findFormat(src, offset, length), false);
        } catch (IOException e) {
            // Handle or rethrow to prevent invalid UTF data from causing crash
            throw e;
        }
    }
    if (src == null || offset < 0 || length < 0 || offset + length > src.length) {
        throw new IllegalArgumentException("Invalid byte array, offset or length");
    }
    JsonParser parser = null;
    try {
        parser = _parserFactory.createParser(src);
        parser = _considerFilter(parser, true);
        return _bindAndReadValues(parser);
    } catch (IOException e) {
        if (parser != null) {
            try {
                parser.close();
            } catch (IOException ignored) { }
        }
        throw e;
    }
}
