public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    // Handle "abs:" prefix case to check for absolute URL presence
    if (attributeKey.startsWith("abs:")) {
        String actualKey = attributeKey.substring(4);
        // Return true if absUrl for the attribute is non-empty
        return !absUrl(actualKey).isEmpty();
    }

    return attributes.hasKey(attributeKey);
}
