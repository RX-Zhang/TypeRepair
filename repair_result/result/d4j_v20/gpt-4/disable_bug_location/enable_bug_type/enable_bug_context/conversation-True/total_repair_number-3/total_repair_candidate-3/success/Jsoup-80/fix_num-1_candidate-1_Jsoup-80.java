void insert(Token.Comment commentToken) {
    Comment comment = new Comment(commentToken.getData());
    Node insert = comment;
    if (commentToken.bogus) { // xml declarations are emitted as bogus comments (which is right for html, but not xml)
        // so we do a bit of a hack and parse the data as an element to pull the attributes out
        String data = comment.getData();
        if (data.length() > 2 && (data.startsWith("!") || data.startsWith("?"))) {
            try {
                Document doc = Jsoup.parse("<" + data.substring(1, data.length() -1) + ">", baseUri, Parser.xmlParser());
                if (doc.childNodeSize() > 0 && doc.childNode(0) instanceof Element) {
                    Element el = (Element) doc.childNode(0);
                    insert = new XmlDeclaration(settings.normalizeTag(el.tagName()), data.startsWith("!"));
                    insert.attributes().addAll(el.attributes());
                }
            } catch (IndexOutOfBoundsException e) {
                // fallback: treat as normal comment if parsing fails
                insert = comment;
            } catch (Exception e) {
                // catch all unexpected exceptions and fallback
                insert = comment;
            }
        }
    }
    insertNode(insert);
}
