protected Object _deserializeFromEmptyString() throws IOException {
    // As per [databind#398], URI requires special handling
    if (_kind == STD_URI) {
        return URI.create("");
    }
    // As per [databind#1123], Locale too
    if (_valueClass == Locale.class) {
        return Locale.ROOT;
    }
    return super._deserializeFromEmptyString();
}
