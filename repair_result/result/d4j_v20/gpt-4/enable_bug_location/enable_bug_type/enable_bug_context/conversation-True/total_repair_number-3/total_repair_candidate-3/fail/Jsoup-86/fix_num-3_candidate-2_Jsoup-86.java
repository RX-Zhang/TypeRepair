public XmlDeclaration asXmlDeclaration() {
    String data = getData();
    XmlDeclaration decl = null;
    if (data.length() > 2) {
        // substring only if data is long enough to avoid IndexOutOfBoundsException
        Document doc = Jsoup.parse("<" + data.substring(1, data.length() -1) + ">", baseUri(), Parser.xmlParser());
        if (doc.childNodeSize() > 0) {
            Element el = doc.child(0);
            decl = new XmlDeclaration(NodeUtils.parser(doc).settings().normalizeTag(el.tagName()), data.startsWith("!"));
            decl.attributes().addAll(el.attributes());
        }
    }
    return decl;
}
