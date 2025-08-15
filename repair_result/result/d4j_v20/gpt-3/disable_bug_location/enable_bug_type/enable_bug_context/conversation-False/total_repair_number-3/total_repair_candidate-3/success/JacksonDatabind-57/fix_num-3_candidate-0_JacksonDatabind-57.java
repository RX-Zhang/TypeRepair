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
    JsonParser parser = null;
    try {
        parser = _parserFactory.createParser(src, offset, length);
        return _bindAndReadValues(_considerFilter(parser, true));
    } catch (CharConversionException e) {
        if (parser != null) {
            try {
                parser.close();
            } catch (IOException ex) {
                // ignore secondary exception on close
            }
        }
        throw e;
    } catch (IOException e) {
        if (parser != null) {
            try {
                parser.close();
            } catch (IOException ex) {
                // ignore secondary exception on close
            }
        }
        throw e;
    }
}
