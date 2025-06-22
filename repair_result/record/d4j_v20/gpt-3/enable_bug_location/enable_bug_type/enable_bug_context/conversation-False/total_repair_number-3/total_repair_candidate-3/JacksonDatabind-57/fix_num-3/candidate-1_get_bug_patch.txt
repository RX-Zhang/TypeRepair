public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        return _detectBindAndReadValues(_dataFormatReaders.findFormat(src, offset, length), false);
    }
    int safeLength = length;
    if (offset < 0) {
        offset = 0;
    }
    if (length < 0 || offset + length > src.length) {
        safeLength = src.length - offset;
    }
    try {
        return _bindAndReadValues(_considerFilter(_parserFactory.createParser(src, offset, safeLength), 
                true));
    } catch (CharConversionException e) {
        // Wrap in IOException to satisfy method signature and propagate error
        throw new IOException("Invalid character encoding in input data", e);
    }
}
