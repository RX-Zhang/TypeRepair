void insert(Token.Comment commentToken) {
    Comment comment = new Comment(commentToken.getData());
    Node insert = comment;
    if (commentToken.bogus) { // xml declarations are emitted as bogus comments (which is right for html, but not xml)
        // so we do a bit of a hack and parse the data as an element to pull the attributes out
        String data = comment.getData();
        if (data.length() > 1 && (data.startsWith("!") || data.startsWith("?"))) {
            try {
                Document doc = Jsoup.parse("<" + data.substring(1, data.length() - 1) + ">", baseUri, Parser.xmlParser());
                if (doc.childNodeSize() > 0) { // Check if the document has any child nodes to avoid IndexOutOfBoundsException
                    Element el = doc.child(0);
                    insert = new XmlDeclaration(settings.normalizeTag(el.tagName()), data.startsWith("!"));
                    insert.attributes().addAll(el.attributes());
                } else {
                    // Handle the case where no valid child nodes were created
                    throw new RuntimeException("Parsed document does not contain valid child nodes.");
                }
            } catch (Exception e) {
                // Handle the exception appropriately (e.g., logging, rethrowing, etc.)
                throw new RuntimeException("Failed to parse XML declaration: " + e.getMessage(), e);
            }
        }
    }
    insertNode(insert);
}
