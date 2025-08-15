public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    if (attributeKey.toLowerCase().startsWith("abs:")) {
        String key = attributeKey.substring("abs:".length());
        String val = absUrl(key);
        return val != null && !val.isEmpty();
    } else {
        return attributes.hasKey(attributeKey);
    }
}
