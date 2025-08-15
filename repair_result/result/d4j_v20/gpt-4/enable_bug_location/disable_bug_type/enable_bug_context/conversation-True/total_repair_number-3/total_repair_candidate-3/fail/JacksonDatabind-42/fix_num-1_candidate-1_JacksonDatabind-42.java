protected T _deserializeFromEmptyString() throws IOException {
    // As per [databind#398], URI requires special handling
    if (_kind == STD_URI) {
        return (T) URI.create("");
    }
    // As per [databind#1123], Locale too
    if (_kind == STD_LOCALE) {
        return (T) Locale.ROOT;
    }
    return super._deserializeFromEmptyString();
}
