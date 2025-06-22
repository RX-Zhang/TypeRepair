final void newAttribute() {
    if (attributes == null)
        attributes = new Attributes();

    if (pendingAttributeName != null) {
        // Trim and sanitize the attribute name to prevent empty or control character names
        pendingAttributeName = pendingAttributeName.trim();
        if (pendingAttributeName.isEmpty() || containsControlChars(pendingAttributeName)) {
            // Invalid attribute name, reset and skip adding the attribute
            pendingAttributeName = null;
            hasEmptyAttributeValue = false;
            hasPendingAttributeValue = false;
            reset(pendingAttributeValue);
            pendingAttributeValueS = null;
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

private boolean containsControlChars(String s) {
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c <= 0x1F || c == 0x7F) // control chars range including DEL
            return true;
    }
    return false;
}
