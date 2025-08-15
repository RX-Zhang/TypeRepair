public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    if (attributeKey.toLowerCase().startsWith("abs:")) {
        String key = attributeKey.substring("abs:".length());
        if (!hasAttr(key))
            return false;
        String absUrl = absUrl(key);
        return absUrl.length() > 0;
    }
    return attributes.hasKey(attributeKey);
}
