void insert(Token.Comment commentToken) {
    Comment comment = new Comment(commentToken.getData());
    Node insert = comment;
    if (commentToken.bogus) { // xml declarations are emitted as bogus comments (which is right for html, but not xml)
        // so we do a bit of a hack and parse the data as an element to pull the attributes out
        String data = comment.getData();
        if (data.length() > 1 && (data.startsWith("!") || data.startsWith("?"))) {
            Document doc = Jsoup.parse("<" + data.substring(1, data.length() - 1) + ">", baseUri, Parser.xmlParser());
            // Check if the document actually has a root element to avoid IndexOutOfBoundsException
            if (!doc.childNodes().isEmpty() && doc.childNode(0) instanceof Element) {
                Element el = (Element) doc.childNode(0);
                insert = new XmlDeclaration(settings.normalizeTag(el.tagName()), data.startsWith("!"));
                insert.attributes().addAll(el.attributes());
            } else {
                // Fallback: treat as plain comment if parsing fails
                insert = comment;
            }
        }
    }
    insertNode(insert);
}
