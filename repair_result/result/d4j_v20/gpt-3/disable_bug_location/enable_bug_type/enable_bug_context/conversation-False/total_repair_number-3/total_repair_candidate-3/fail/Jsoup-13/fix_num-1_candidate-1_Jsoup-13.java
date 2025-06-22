public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    if (attributeKey.toLowerCase().startsWith("abs:")) {
        String realKey = attributeKey.substring(4);
        String val = absUrl(realKey);
        return val != null && val.length() > 0;
    }
    return attributes.hasKey(attributeKey);
}
