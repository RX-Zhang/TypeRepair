final void newAttribute() {
    if (attributes == null)
        attributes = new Attributes();

    if (pendingAttributeName != null) {
        // the tokeniser has skipped whitespace control chars, but trimming could collapse to empty for other control codes, so verify here
        pendingAttributeName = pendingAttributeName.trim();
        if (pendingAttributeName.isEmpty()) {
            // if after trimming the name is empty, discard the attribute and do not add it
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
            pendingAttributeName = null;
        }
    }
    hasEmptyAttributeValue = false;
    hasPendingAttributeValue = false;
    reset(pendingAttributeValue);
    pendingAttributeValueS = null;
}
