Element insert(Token.StartTag startTag) {
    // handle empty unknown and known tags
    // for known self closing tags (empty elements), insertEmpty handles them fully; no need to add to stack or emit end tag here
    if (startTag.isSelfClosing()) {
        Tag tag = Tag.valueOf(startTag.name());
        if (tag.isKnownTag() && tag.isSelfClosing()) {
            // known self closing tag: insert and return without further processing
            return insertEmpty(startTag);
        } else {
            // unknown tag or not self closing tag: treat as empty (self closing) with emitted end tag
            Element el = insertEmpty(startTag);
            stack.add(el);
            tokeniser.emit(new Token.EndTag(el.tagName()));  // ensure we get out of whatever state we are in. emitted for yielded processing
            return el;
        }
    }
    
    Element el = new Element(Tag.valueOf(startTag.name()), baseUri, startTag.attributes);
    insert(el);
    return el;
}
