public XmlDeclaration asXmlDeclaration() {
    String data = getData();
    if (data.length() < 2) {
        return null; // or handle empty/invalid data case appropriately
    }
    Document doc = Jsoup.parse("<" + data.substring(1, data.length() - 1) + ">", baseUri(), Parser.xmlParser());
    XmlDeclaration decl = null;
    if (doc.childNodeSize() > 0) {
        Node firstChild = doc.childNode(0);
        if (firstChild instanceof Element) {
            Element el = (Element) firstChild;
            decl = new XmlDeclaration(NodeUtils.parser(doc).settings().normalizeTag(el.tagName()), data.startsWith("!"));
            decl.attributes().addAll(el.attributes());
        }
    }
    return decl;
}
