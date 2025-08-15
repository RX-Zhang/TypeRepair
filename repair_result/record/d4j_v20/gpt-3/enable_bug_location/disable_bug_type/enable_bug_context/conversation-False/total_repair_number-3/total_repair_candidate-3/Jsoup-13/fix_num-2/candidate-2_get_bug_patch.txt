public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);
    if (attributeKey.toLowerCase().startsWith("abs:")) {
        String realKey = attributeKey.substring("abs:".length());
        String absUrl = absUrl(realKey);
        return absUrl != null && !absUrl.isEmpty();
    }
    return attributes.hasKey(attributeKey);
}
