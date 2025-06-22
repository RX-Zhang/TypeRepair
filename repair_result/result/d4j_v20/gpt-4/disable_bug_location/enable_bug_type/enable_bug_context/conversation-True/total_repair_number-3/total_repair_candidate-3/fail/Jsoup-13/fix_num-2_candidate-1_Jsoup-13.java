public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    if (attributeKey.startsWith("abs:")) {
        String key = attributeKey.substring(4); // remove "abs:" prefix
        String absVal = absUrl(key);
        return absVal != null && !absVal.isEmpty();
    } else {
        return attributes.hasKey(attributeKey);
    }
}
