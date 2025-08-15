Element insert(Token.StartTag startTag) {
    // handle empty unknown tags
    // when the spec expects an empty tag, will directly hit insertEmpty, so won't generate this fake end tag.
    if (startTag.isSelfClosing()) {
        Element el = insertEmpty(startTag);
        // DO NOT add empty element to stack
        // DO NOT emit fake end tag for known empty tags, only for unknown tags
        if (!Tag.valueOf(startTag.name()).isKnownTag()) {
            stack.add(el);
            tokeniser.emit(new Token.EndTag(el.tagName()));  // ensure we get out of whatever state we are in. emitted for yielded processing
        }
        return el;
    }
    
    Element el = new Element(Tag.valueOf(startTag.name()), baseUri, startTag.attributes);
    insert(el);
    return el;
}
