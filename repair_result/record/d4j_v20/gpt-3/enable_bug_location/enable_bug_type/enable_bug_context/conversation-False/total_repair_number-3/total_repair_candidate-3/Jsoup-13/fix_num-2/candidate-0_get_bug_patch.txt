public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    // Check for both the original attribute and the absolute attribute
    return attributes.hasKey(attributeKey) || attributes.hasKey("abs:" + attributeKey);
}
