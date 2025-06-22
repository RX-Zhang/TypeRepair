public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    if (attributeKey.startsWith("abs:")) {
        String actualKey = attributeKey.substring(4);
        String absUrl = absUrl(actualKey);
        return absUrl != null && !absUrl.isEmpty();
    }
    return attributes.hasKey(attributeKey);
}
