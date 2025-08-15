public XmlDeclaration asXmlDeclaration() {
    String data = getData();
    if (data == null || data.length() < 2) {
        return null;
    }
    Document doc;
    try {
        doc = Jsoup.parse("<" + data.substring(1, data.length() - 1) + ">", baseUri(), Parser.xmlParser());
    } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
        // Prevent runtime errors by returning null if parsing fails
        return null;
    }
    XmlDeclaration decl = null;
    if (doc.childNodeSize() > 0) {
        Element el = doc.child(0);
        if (el != null) {
            decl = new XmlDeclaration(NodeUtils.parser(doc).settings().normalizeTag(el.tagName()), data.startsWith("!"));
            decl.attributes().addAll(el.attributes());
        }
    }
    return decl;
}
