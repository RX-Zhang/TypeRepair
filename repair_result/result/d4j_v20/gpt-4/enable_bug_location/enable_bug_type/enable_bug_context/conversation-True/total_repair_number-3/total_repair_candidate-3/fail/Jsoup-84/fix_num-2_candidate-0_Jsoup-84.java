public void head(org.jsoup.nodes.Node source, int depth) {
    namespacesStack.push(new HashMap<>(namespacesStack.peek())); // inherit from above on the stack
    if (source instanceof org.jsoup.nodes.Element) {
        org.jsoup.nodes.Element sourceEl = (org.jsoup.nodes.Element) source;

        String prefix = updateNamespaces(sourceEl);
        String namespace = namespacesStack.peek().get(prefix);
        String tagName = sourceEl.tagName();

        // Handle undeclared namespaces by passing null as namespace
        if (namespace == null || namespace.isEmpty()) {
            namespace = null;
            // Separate localName and nodeName for prefixed tags
            String localName = tagName;
            if (tagName.contains(":")) {
                localName = tagName.substring(tagName.indexOf(':') + 1);
            }
            Element el =
                doc.createElementNS(namespace, tagName); // tagName used as nodeName
            copyAttributes(sourceEl, el);
            if (dest == null) { // sets up the root
                doc.appendChild(el);
            } else {
                dest.appendChild(el);
            }
            dest = el; // descend
        } else {
            Element el =
                doc.createElementNS(namespace, tagName);
            copyAttributes(sourceEl, el);
            if (dest == null) { // sets up the root
                doc.appendChild(el);
            } else {
                dest.appendChild(el);
            }
            dest = el; // descend
        }
    } else if (source instanceof org.jsoup.nodes.TextNode) {
        org.jsoup.nodes.TextNode sourceText = (org.jsoup.nodes.TextNode) source;
        Text text = doc.createTextNode(sourceText.getWholeText());
        dest.appendChild(text);
    } else if (source instanceof org.jsoup.nodes.Comment) {
        org.jsoup.nodes.Comment sourceComment = (org.jsoup.nodes.Comment) source;
        Comment comment = doc.createComment(sourceComment.getData());
        dest.appendChild(comment);
    } else if (source instanceof org.jsoup.nodes.DataNode) {
        org.jsoup.nodes.DataNode sourceData = (org.jsoup.nodes.DataNode) source;
        Text node = doc.createTextNode(sourceData.getWholeData());
        dest.appendChild(node);
    } else {
        // unhandled
    }
}
