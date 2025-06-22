final void newAttribute() {
    if (attributes == null)
        attributes = new Attributes();

    if (pendingAttributeName != null) {
        // trim and sanitize the attribute name to avoid empty or control character names
        String trimmedName = pendingAttributeName.trim();
        if (!trimmedName.isEmpty() && !containsControlCharacters(trimmedName)) {
            pendingAttributeName = trimmedName;
            Attribute attribute;
            if (hasPendingAttributeValue)
                attribute = new Attribute(pendingAttributeName,
                    (pendingAttributeValue != null && pendingAttributeValue.length() > 0) ? pendingAttributeValue.toString() : pendingAttributeValueS);
            else if (hasEmptyAttributeValue)
                attribute = new Attribute(pendingAttributeName, "");
            else
                attribute = new BooleanAttribute(pendingAttributeName);
            attributes.put(attribute);
        }
        // else ignore invalid attribute name input
    }
    pendingAttributeName = null;
    hasEmptyAttributeValue = false;
    hasPendingAttributeValue = false;
    reset(pendingAttributeValue);
    pendingAttributeValueS = null;
}

private boolean containsControlCharacters(String s) {
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c <= 0x1F || c == 0x7F) {
            return true;
        }
    }
    return false;
}
