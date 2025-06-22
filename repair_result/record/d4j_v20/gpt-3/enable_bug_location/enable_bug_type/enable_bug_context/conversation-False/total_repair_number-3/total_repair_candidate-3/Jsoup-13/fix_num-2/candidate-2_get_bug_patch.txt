public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    // Check for both normal and "abs:" prefixed attributes
    return attributes.hasKey(attributeKey) || attributes.hasKey("abs:" + attributeKey);
}
