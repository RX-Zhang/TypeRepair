public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
    throws IOException, JsonProcessingException
{
    if (_dataFormatReaders != null) {
        return _detectBindAndReadValues(_dataFormatReaders.findFormat(src, offset, length), false);
    }
    JsonParser parser = null;
    try {
        parser = _parserFactory.createParser(src, offset, length);
        return _bindAndReadValues(_considerFilter(parser, true));
    } catch (CharConversionException e) {
        // Wrap or rethrow with additional info if needed, or handle gracefully
        throw e;
    } catch (IOException e) {
        // Possibly handle other IOExceptions as well
        throw e;
    } finally {
        if (parser != null) {
            try {
                parser.close();
            } catch (IOException ignored) {
                // ignore exception on close
            }
        }
    }
}
