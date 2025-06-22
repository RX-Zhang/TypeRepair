public void head(org.jsoup.nodes.Node source, int depth) {
    namespacesStack.push(new HashMap<>(namespacesStack.peek())); // inherit from above on the stack
    if (source instanceof org.jsoup.nodes.Element) {
        org.jsoup.nodes.Element sourceEl = (org.jsoup.nodes.Element) source;

        String prefix = updateNamespaces(sourceEl);
        // null or empty namespace causes createElementNS to throw NAMESPACE_ERR
        // per test, undeclared namespace should be treated as null namespace (no namespace)
        String namespace = namespacesStack.peek().get(prefix);
        if (namespace == null || namespace.isEmpty()) {
            // createElementNS with null namespace uses no namespace, safe here
            namespace = null;
        }
        String tagName = sourceEl.tagName();

        Element el = 
            doc.createElementNS(namespace, tagName);
        copyAttributes(sourceEl, el);
        if (dest == null) { // sets up the root
            doc.appendChild(el);
        } else {
            dest.appendChild(el);
        }
        dest = el; // descend
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
