public String nextTextValue() throws IOException
{
    _binaryValue = null;
    if (_nextToken != null) {
        JsonToken t = _nextToken;
        _currToken = t;
        _nextToken = null;

        // expected case; yes, got a String
        if (t == JsonToken.VALUE_STRING) {
            return _currText;
        }
        _updateState(t);
        return null;
    }

    JsonToken t = nextToken();

    if (t == JsonToken.VALUE_STRING) {
        return _currText;
    }
    return null;
}
