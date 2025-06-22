public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    if (attributeKey.toLowerCase().startsWith("abs:")) {
        String key = attributeKey.substring("abs:".length());
        return absUrl(key).length() > 0;
    } else {
        return attributes.hasKey(attributeKey);
    }
}
