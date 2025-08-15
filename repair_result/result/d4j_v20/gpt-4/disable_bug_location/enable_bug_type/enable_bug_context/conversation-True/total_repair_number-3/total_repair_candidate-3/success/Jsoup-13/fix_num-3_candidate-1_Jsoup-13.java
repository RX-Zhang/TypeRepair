public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    if (attributes.hasKey(attributeKey)) {
        return true;
    } else if (attributeKey.toLowerCase().startsWith("abs:")) {
        String key = attributeKey.substring("abs:".length());
        // Return true if absUrl is not empty (meaning there is an absolute URL)
        return !absUrl(key).isEmpty();
    } else {
        return false;
    }
}
