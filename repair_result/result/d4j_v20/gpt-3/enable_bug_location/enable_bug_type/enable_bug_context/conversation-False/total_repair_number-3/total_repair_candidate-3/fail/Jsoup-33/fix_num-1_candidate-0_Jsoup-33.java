Element insert(Token.StartTag startTag) {
    // handle empty unknown tags
    // when the spec expects an empty tag, will directly hit insertEmpty, so won't generate this fake end tag.
    if (startTag.isSelfClosing()) {
        Element el = insertEmpty(startTag);
        stack.add(el);
        tokeniser.emit(new Token.EndTag(el.tagName()));  // ensure we get out of whatever state we are in. emitted for yielded processing
        return el;
    }
    
    // Verify and convert variable types before creating the Element
    String tagName = startTag.name();
    if (tagName == null || tagName.isEmpty()) {
        throw new IllegalArgumentException("Tag name cannot be null or empty");
    }
    
    Element el = new Element(Tag.valueOf(tagName), baseUri, startTag.attributes);
    insert(el);
    return el;
}
