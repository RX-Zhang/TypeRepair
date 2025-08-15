final void newAttribute() {
    if (attributes == null)
        attributes = new Attributes();

    if (pendingAttributeName != null) {
        // the tokeniser has skipped whitespace control chars, but trimming could collapse to empty for other control codes, so verify here
        pendingAttributeName = pendingAttributeName.trim();
        // Robust input validation: ignore empty or control character only attribute names.
        if (!pendingAttributeName.isEmpty() && !containsControlCharacters(pendingAttributeName)) {
            Attribute attribute;
            if (hasPendingAttributeValue)
                attribute = new Attribute(pendingAttributeName,
                    pendingAttributeValue != null && pendingAttributeValue.length() > 0 
                        ? pendingAttributeValue.toString() 
                        : pendingAttributeValueS);
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

private boolean containsControlCharacters(String s) {
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c <= 0x1F || c == 0x7F) {
            return true;
        }
    }
    return false;
}
