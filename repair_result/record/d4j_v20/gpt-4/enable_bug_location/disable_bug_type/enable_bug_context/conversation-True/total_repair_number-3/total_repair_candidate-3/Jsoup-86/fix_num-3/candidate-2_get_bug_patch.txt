public XmlDeclaration asXmlDeclaration() {
    String data = getData();
    if (data.length() <= 1) {
        return null;
    }
    String toParse = data.substring(1, data.length() - 1);
    if (toParse.isEmpty()) {
        return null;
    }
    Document doc = Jsoup.parse("<" + toParse + ">", baseUri(), Parser.xmlParser());
    XmlDeclaration decl = null;
    if (doc.childNodeSize() > 0) {
        Element el = doc.child(0);
        decl = new XmlDeclaration(NodeUtils.parser(doc).settings().normalizeTag(el.tagName()), data.startsWith("!"));
        decl.attributes().addAll(el.attributes());
    }
    return decl;
}
