public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    // Check for both the original attribute and the prefixed "abs:" attribute
    return attributes.hasKey(attributeKey) || attributes.hasKey("abs:" + attributeKey);
}
