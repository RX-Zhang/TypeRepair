final void newAttribute() {
    if (attributes == null)
        attributes = new Attributes();

    if (pendingAttributeName != null) {
        // Trim and sanitize the attribute name to avoid empty or control characters
        pendingAttributeName = pendingAttributeName.trim();
        if (pendingAttributeName.isEmpty()) {
            // If the attribute name is empty after trimming, discard it
            pendingAttributeName = null;
        } else {
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
