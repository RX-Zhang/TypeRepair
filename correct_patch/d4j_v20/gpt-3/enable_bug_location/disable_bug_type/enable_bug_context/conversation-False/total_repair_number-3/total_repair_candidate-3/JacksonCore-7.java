public int writeValue() {
    if (_type == TYPE_OBJECT) {
        // Writing a value inside an object means we must have gotten a field name
        if (!_gotName) {
            // Error: value written without field name
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

    // Root context
    ++_index;
    return (_index == 0) ? STATUS_OK_AS_IS : STATUS_OK_AFTER_SPACE;
}
