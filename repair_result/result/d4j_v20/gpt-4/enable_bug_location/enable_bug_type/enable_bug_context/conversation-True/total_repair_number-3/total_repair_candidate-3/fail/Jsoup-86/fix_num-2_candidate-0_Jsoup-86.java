public XmlDeclaration asXmlDeclaration() {
    String data = getData();
    if (data == null || data.length() < 2) {
        return null;
    }
    String inner = data.substring(1, data.length() -1);
    Document doc;
    try {
        doc = Jsoup.parse("<" + inner + ">", baseUri(), Parser.xmlParser());
    } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
        // Defensive: return null or handle gracefully if parsing fails
        return null;
    }
    XmlDeclaration decl = null;
    if (doc.childNodeSize() > 0) {
        Element el = doc.child(0);
        decl = new XmlDeclaration(NodeUtils.parser(doc).settings().normalizeTag(el.tagName()), data.startsWith("!"));
        decl.attributes().addAll(el.attributes());
    }
    return decl;
}
