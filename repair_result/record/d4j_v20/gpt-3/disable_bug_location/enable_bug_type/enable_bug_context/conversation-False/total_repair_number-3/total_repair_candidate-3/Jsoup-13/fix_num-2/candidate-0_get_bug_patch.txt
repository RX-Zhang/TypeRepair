public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    if (attributeKey.toLowerCase().startsWith("abs:")) {
        String key = attributeKey.substring("abs:".length());
        String abs = absUrl(key);
        return !abs.isEmpty();
    }
    return attributes.hasKey(attributeKey);
}
