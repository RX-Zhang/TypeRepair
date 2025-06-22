Element insert(Token.StartTag startTag) {
    // handle empty unknown tags
    // when the spec expects an empty tag, will directly hit insertEmpty, so won't generate this fake end tag.
    if (startTag.isSelfClosing()) {
        Element el = insertEmpty(startTag);
        // For known tags, do not add to the stack or emit end tag here; insertEmpty handles the empty insert correctly.
        // For unknown tags, insertEmpty takes care of self-closing and no further processing needed.
        return el;
    }
    
    Element el = new Element(Tag.valueOf(startTag.name()), baseUri, startTag.attributes);
    insert(el);
    return el;
}
