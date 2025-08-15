public String getValue() {
    // Return empty string if this is a boolean attribute with null value
    if (isBooleanAttribute() && val == null) {
        return "";
    }
    return val;
}
