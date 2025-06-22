public XmlDeclaration asXmlDeclaration() {
    String data = getData();
    XmlDeclaration decl = null;
    try {
        if (data.length() > 2) {
            Document doc = Jsoup.parse("<" + data.substring(1, data.length() - 1) + ">", baseUri(), Parser.xmlParser());
            if (doc.childNodeSize() > 0) {
                Element el = doc.child(0);
                decl = new XmlDeclaration(NodeUtils.parser(doc).settings().normalizeTag(el.tagName()), data.startsWith("!"));
                decl.attributes().addAll(el.attributes());
            }
        }
    } catch (Exception e) {
        // handle or log exception if needed, or silently ignore so no runtime crash
    }
    return decl;
}
