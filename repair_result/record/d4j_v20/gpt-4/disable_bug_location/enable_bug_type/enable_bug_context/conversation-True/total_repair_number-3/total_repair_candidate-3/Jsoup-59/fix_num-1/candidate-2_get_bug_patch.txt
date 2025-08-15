final void newAttribute() {
    if (attributes == null)
        attributes = new Attributes();

    if (pendingAttributeName != null) {
        // trim and validate attribute name after removing control chars
        pendingAttributeName = pendingAttributeName.trim();
        // Reject empty or control-character-only attribute names to avoid IllegalArgumentException
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
