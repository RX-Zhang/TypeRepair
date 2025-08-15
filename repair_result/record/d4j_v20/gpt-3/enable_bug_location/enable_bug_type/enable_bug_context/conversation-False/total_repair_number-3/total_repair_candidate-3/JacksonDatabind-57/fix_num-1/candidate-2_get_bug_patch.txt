public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        return _detectBindAndReadValues(_dataFormatReaders.findFormat(src, offset, length), false);
    }
    try {
        return _bindAndReadValues(_considerFilter(_parserFactory.createParser(src, offset, length), 
                true));
    } catch (CharConversionException e) {
        // Handle or wrap the CharConversionException to provide clearer context or rethrow
        throw new IOException("Invalid character encoding detected while parsing input bytes", e);
    }
}
