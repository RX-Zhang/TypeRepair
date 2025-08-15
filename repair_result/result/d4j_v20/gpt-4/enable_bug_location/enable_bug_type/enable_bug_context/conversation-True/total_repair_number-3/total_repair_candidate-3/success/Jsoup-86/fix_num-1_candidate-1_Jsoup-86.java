public XmlDeclaration asXmlDeclaration() {
    String data = getData();
    if (data.length() < 2) {
        return null; // Not enough length to substring safely
    }
    XmlDeclaration decl = null;
    try {
        Document doc = Jsoup.parse("<" + data.substring(1, data.length() -1) + ">", baseUri(), Parser.xmlParser());
        if (doc.childNodeSize() > 0) {
            Element el = doc.child(0);
            decl = new XmlDeclaration(NodeUtils.parser(doc).settings().normalizeTag(el.tagName()), data.startsWith("!"));
            decl.attributes().addAll(el.attributes());
        }
    } catch (Exception e) {
        // Exception during parsing: return null or handle as needed
        decl = null;
    }
    return decl;
}
