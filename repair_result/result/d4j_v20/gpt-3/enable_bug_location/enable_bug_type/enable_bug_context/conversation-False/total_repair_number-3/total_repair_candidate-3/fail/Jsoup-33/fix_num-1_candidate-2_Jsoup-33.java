Element insert(Token.StartTag startTag) {
    // Handle empty unknown tags
    // When the spec expects an empty tag, will directly hit insertEmpty, so won't generate this fake end tag.
    if (startTag != null && startTag.isSelfClosing()) {
        Element el = insertEmpty(startTag);
        stack.add(el);
        tokeniser.emit(new Token.EndTag(el.tagName()));  // Ensure we get out of whatever state we are in. Emitted for yielded processing
        return el;
    }
    
    if (startTag != null) {
        Element el = new Element(Tag.valueOf(startTag.name()), baseUri, startTag.attributes);
        insert(el);
        return el;
    }
    
    throw new IllegalArgumentException("StartTag cannot be null");
}
