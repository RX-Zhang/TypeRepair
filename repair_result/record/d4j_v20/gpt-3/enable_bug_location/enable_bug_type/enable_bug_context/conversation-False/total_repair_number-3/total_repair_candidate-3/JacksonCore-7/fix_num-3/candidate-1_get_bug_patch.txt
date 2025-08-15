public int writeValue() {
    // Most likely, object:
    if (_type == TYPE_OBJECT) {
        // When writing a value inside an object, we must have gotten a field name first.
        // Resetting _gotName is correct.
        _gotName = false;
        ++_index;
        // After a field name, value follows: after colon status.
        return STATUS_OK_AFTER_VALUE; // FIXED: should return STATUS_OK_AFTER_VALUE, not STATUS_OK_AFTER_COLON
    }

    // Ok, array?
    if (_type == TYPE_ARRAY) {
        int ix = _index;
        ++_index;
        // For first element, no comma; later elements after comma.
        return (ix < 0) ? STATUS_OK_AS_IS : STATUS_OK_AFTER_COMMA;
    }

    // Nope, root context
    // No commas within root context, but need space
    ++_index;
    return (_index == 0) ? STATUS_OK_AS_IS : STATUS_OK_AFTER_SPACE;
}
