public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    // Check both the original attribute and the absolute prefixed attribute
    return attributes.hasKey(attributeKey) || attributes.hasKey("abs:" + attributeKey);
}
