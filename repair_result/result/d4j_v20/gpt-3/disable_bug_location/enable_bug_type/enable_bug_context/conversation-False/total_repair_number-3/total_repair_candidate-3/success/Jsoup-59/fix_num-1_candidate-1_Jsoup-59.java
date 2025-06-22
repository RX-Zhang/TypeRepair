final void newAttribute() {
    if (attributes == null)
        attributes = new Attributes();

    if (pendingAttributeName != null) {
        // the tokenizer has skipped whitespace control chars, but trimming could collapse to empty for other control codes, so verify here
        pendingAttributeName = pendingAttributeName.trim();
        // Validate that the attribute name is not empty after trimming and does not contain control characters
        if (!pendingAttributeName.isEmpty() && !containsControlChars(pendingAttributeName)) {
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

private boolean containsControlChars(String s) {
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        // Control characters are in the range 0x00 - 0x1F and 0x7F
        if (c <= 0x1F || c == 0x7F) {
            return true;
        }
    }
    return false;
}
