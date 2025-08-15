public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        try {
            return _detectBindAndReadValues(_dataFormatReaders.findFormat(src, offset, length), false);
        } catch (CharConversionException e) {
            throw new IOException("Invalid UTF-32 character detected in input", e);
        }
    }
    return _bindAndReadValues(_considerFilter(_parserFactory.createParser(src, offset, length), 
            true));
}
