Element insert(Token.StartTag startTag) {
    // handle empty known tags and unknown tags that are self closing
    Tag tag = Tag.valueOf(startTag.name());

    if (startTag.isSelfClosing()) {
        Element el = insertEmpty(startTag);
        if (tag.isKnownTag()) {
            stack.add(el);
            tokeniser.emit(new Token.EndTag(el.tagName()));  // ensure state exit, emitted for yielded processing
        }
        return el;
    }
    
    Element el = new Element(tag, baseUri, startTag.attributes);
    insert(el);
    return el;
}
