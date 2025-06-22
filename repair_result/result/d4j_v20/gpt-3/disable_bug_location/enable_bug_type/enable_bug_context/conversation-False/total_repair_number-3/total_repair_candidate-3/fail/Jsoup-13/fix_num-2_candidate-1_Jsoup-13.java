public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);
    if (attributeKey.toLowerCase().startsWith("abs:")) {
        String key = attributeKey.substring("abs:".length());
        return hasAttr(key) && !absUrl(key).isEmpty();
    }
    return attributes.hasKey(attributeKey);
}
