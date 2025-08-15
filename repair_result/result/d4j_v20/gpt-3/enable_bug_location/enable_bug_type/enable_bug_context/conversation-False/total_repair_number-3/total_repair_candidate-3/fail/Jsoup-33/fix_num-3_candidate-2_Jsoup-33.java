Element insert(Token.StartTag startTag) {
    // handle empty unknown tags
    // when the spec expects an empty tag, will directly hit insertEmpty, so won't generate this fake end tag.
    if (startTag.isSelfClosing()) {
        Element el = insertEmpty(startTag);
        stack.add(el);
        return el; // Remove the tokeniser.emit line to prevent emitting an end tag for self-closing elements
    }
    
    Element el = new Element(Tag.valueOf(startTag.name()), baseUri, startTag.attributes);
    insert(el);
    return el;
}
