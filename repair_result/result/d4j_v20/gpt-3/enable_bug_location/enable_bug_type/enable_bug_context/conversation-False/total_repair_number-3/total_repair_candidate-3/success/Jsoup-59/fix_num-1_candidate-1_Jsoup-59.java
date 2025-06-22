final void newAttribute() {
    if (attributes == null)
        attributes = new Attributes();

    if (pendingAttributeName != null) {
        // sanitize the attribute name by removing control characters and trimming whitespace
        String sanitizedAttributeName = pendingAttributeName.replaceAll("[\\x00-\\x1F\\x7F]", "").trim();
        if (!sanitizedAttributeName.isEmpty()) {
            Attribute attribute;
            if (hasPendingAttributeValue)
                attribute = new Attribute(sanitizedAttributeName,
                    pendingAttributeValue.length() > 0 ? pendingAttributeValue.toString() : pendingAttributeValueS);
            else if (hasEmptyAttributeValue)
                attribute = new Attribute(sanitizedAttributeName, "");
            else
                attribute = new BooleanAttribute(sanitizedAttributeName);
            attributes.put(attribute);
        }
    }
    pendingAttributeName = null;
    hasEmptyAttributeValue = false;
    hasPendingAttributeValue = false;
    reset(pendingAttributeValue);
    pendingAttributeValueS = null;
}
