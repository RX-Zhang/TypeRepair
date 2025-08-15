final void newAttribute() {
    if (attributes == null)
        attributes = new Attributes();

    if (pendingAttributeName != null) {
        // the tokeniser has skipped whitespace control chars, but trimming could collapse to empty for other control codes, so verify here
        String name = pendingAttributeName.trim();
        if (!name.isEmpty()) {
            Attribute attribute;
            if (hasPendingAttributeValue)
                attribute = new Attribute(name,
                    pendingAttributeValue.length() > 0 ? pendingAttributeValue.toString() : pendingAttributeValueS);
            else if (hasEmptyAttributeValue)
                attribute = new Attribute(name, "");
            else
                attribute = new BooleanAttribute(name);
            attributes.put(attribute);
        }
    }
    pendingAttributeName = null;
    hasEmptyAttributeValue = false;
    hasPendingAttributeValue = false;
    reset(pendingAttributeValue);
    pendingAttributeValueS = null;
}
