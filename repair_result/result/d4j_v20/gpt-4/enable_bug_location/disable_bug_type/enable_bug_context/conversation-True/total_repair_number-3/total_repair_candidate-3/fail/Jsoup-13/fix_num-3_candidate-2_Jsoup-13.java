public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    // Check for the attribute with and without the "abs:" prefix
    return attributes.hasKey(attributeKey) || attributes.hasKey("abs:" + attributeKey);
}
