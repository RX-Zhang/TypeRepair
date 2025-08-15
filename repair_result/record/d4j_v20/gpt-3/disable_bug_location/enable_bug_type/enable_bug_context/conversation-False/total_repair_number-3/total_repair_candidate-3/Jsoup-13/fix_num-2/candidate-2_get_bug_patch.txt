public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    if (attributeKey.toLowerCase().startsWith("abs:")) {
        String key = attributeKey.substring("abs:".length());
        if (!attributes.hasKey(key))
            return false;
        // try to get abs url; if empty, treat as no attr
        return !absUrl(key).isEmpty();
    } else {
        return attributes.hasKey(attributeKey);
    }
}
