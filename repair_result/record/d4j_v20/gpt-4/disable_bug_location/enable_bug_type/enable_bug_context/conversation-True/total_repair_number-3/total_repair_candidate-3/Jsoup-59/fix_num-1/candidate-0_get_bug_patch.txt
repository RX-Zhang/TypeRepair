final void newAttribute() {
    if (attributes == null)
        attributes = new Attributes();

    if (pendingAttributeName != null) {
        pendingAttributeName = pendingAttributeName.trim();
        // Validate attribute name after trimming to ensure it is not empty or invalid
        if (!pendingAttributeName.isEmpty()) {
            Attribute attribute;
            if (hasPendingAttributeValue) {
                String value = (pendingAttributeValue.length() > 0) ? pendingAttributeValue.toString() : pendingAttributeValueS;
                // Sanitize value: trim and ensure not empty if needed (optional)
                if (value != null) {
                    value = value.trim();
                }
                attribute = new Attribute(pendingAttributeName, value);
            } else if (hasEmptyAttributeValue) {
                attribute = new Attribute(pendingAttributeName, "");
            } else {
                attribute = new BooleanAttribute(pendingAttributeName);
            }
            attributes.put(attribute);
        }
    }
    pendingAttributeName = null;
    hasEmptyAttributeValue = false;
    hasPendingAttributeValue = false;
    reset(pendingAttributeValue);
    pendingAttributeValueS = null;
}
