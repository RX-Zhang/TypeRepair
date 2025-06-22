public int writeValue() {
    if (_type == TYPE_OBJECT) {
        // In object value context, we expect a field name first before a value
        if (!_gotName) {
            return STATUS_EXPECT_NAME;
        }
        _gotName = false;
        ++_index;
        return STATUS_OK_AFTER_COLON;
    }

    if (_type == TYPE_ARRAY) {
        int ix = _index;
        ++_index;
        return (ix < 0) ? STATUS_OK_AS_IS : STATUS_OK_AFTER_COMMA;
    }

    // Root context: no commas, but need spaces if after first value
    ++_index;
    return (_index == 0) ? STATUS_OK_AS_IS : STATUS_OK_AFTER_SPACE;
}
