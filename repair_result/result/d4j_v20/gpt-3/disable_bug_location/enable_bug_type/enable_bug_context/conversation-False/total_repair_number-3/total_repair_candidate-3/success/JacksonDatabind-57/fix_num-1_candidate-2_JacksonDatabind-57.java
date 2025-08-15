public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        DataFormatReaders.Match match = _dataFormatReaders.findFormat(src, offset, length);
        if (!match.hasMatch()) {
            // If no match found, handle gracefully to prevent decoding errors
            throw new JsonProcessingException("No matching format found for input data") {};
        }
        return _detectBindAndReadValues(match, false);
    }
    try {
        return _bindAndReadValues(_considerFilter(_parserFactory.createParser(src, offset, length), 
                true));
    } catch (CharConversionException e) {
        // Wrap and rethrow as IOException with meaningful message to prevent raw execution error
        throw new IOException("Invalid UTF-32 character found during parsing", e);
    }
}
