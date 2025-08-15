public boolean hasAttr(String attributeKey) {
    Validate.notNull(attributeKey);

    if (attributes.hasKey(attributeKey))
        return true;
    else if (attributeKey.toLowerCase().startsWith("abs:")) {
        return absUrl(attributeKey.substring("abs:".length())).length() > 0;
    } else {
        return false;
    }
}
