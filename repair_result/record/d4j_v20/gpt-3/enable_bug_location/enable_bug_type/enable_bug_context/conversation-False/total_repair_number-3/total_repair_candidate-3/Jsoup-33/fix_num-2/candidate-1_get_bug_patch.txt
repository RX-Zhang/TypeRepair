Element insert(Token.StartTag startTag) {
    // handle empty unknown tags
    // when the spec expects an empty tag, will directly hit insertEmpty, so won't generate this fake end tag.
    if (startTag.isSelfClosing()) {
        Element el = insertEmpty(startTag);
        stack.add(el);
        // Emit end tag only for known self-closing tags
        if (el.tagName().equals("img") || el.tagName().equals("br") || el.tagName().equals("hr") || el.tagName().equals("input") || el.tagName().equals("meta") || el.tagName().equals("link")) {
            tokeniser.emit(new Token.EndTag(el.tagName()));  // ensure we get out of whatever state we are in. emitted for yielded processing
        }
        return el;
    }
    
    Element el = new Element(Tag.valueOf(startTag.name()), baseUri, startTag.attributes);
    insert(el);
    return el;
}
