final void newAttribute() {
    if (attributes == null)
        attributes = new Attributes();

    if (pendingAttributeName != null) {
        // the tokeniser has skipped whitespace control chars, but trimming could collapse to empty for other control codes, so verify here
        pendingAttributeName = pendingAttributeName.trim();
        
        // Validate the attribute name is not empty after trimming
        if (pendingAttributeName.isEmpty()) {
            // Reset and skip adding this attribute since the name is invalid
            reset(pendingAttributeValue);
            pendingAttributeValueS = null;
            hasEmptyAttributeValue = false;
            hasPendingAttributeValue = false;
            return;
        }

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
    pendingAttributeName = null;
    hasEmptyAttributeValue = false;
    hasPendingAttributeValue = false;
    reset(pendingAttributeValue);
    pendingAttributeValueS = null;
}
