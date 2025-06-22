final void newAttribute() {
    if (attributes == null)
        attributes = new Attributes();

    if (pendingAttributeName != null) {
        // Trim and validate the attribute name to ensure it is not empty after removing control chars
        pendingAttributeName = pendingAttributeName.trim();
        // Remove any control characters from attribute name to prevent invalid or empty names
        pendingAttributeName = pendingAttributeName.replaceAll("[\\p{Cntrl}]", "");
        if (!pendingAttributeName.isEmpty()) {
            Attribute attribute;
            if (hasPendingAttributeValue)
                attribute = new Attribute(pendingAttributeName,
                    pendingAttributeValue.length() > 0 ? pendingAttributeValue.toString() : pendingAttributeValueS);
            else if (hasEmptyAttributeValue)
                attribute = new Attribute(pendingAttributeName, "");
            else
                attribute = new BooleanAttribute(pendingAttributeName);
            attributes.put(attribute);
        }
    }
    pendingAttributeName = null;
    hasEmptyAttributeValue = false;
    hasPendingAttributeValue = false;
    reset(pendingAttributeValue);
    pendingAttributeValueS = null;
}
