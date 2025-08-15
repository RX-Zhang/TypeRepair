public void head(org.jsoup.nodes.Node source, int depth) {
    namespacesStack.push(new HashMap<>(namespacesStack.peek())); // inherit from above on the stack
    if (source instanceof org.jsoup.nodes.Element) {
        org.jsoup.nodes.Element sourceEl = (org.jsoup.nodes.Element) source;

        String prefix = updateNamespaces(sourceEl);

        // Fix: If prefix is not bound (namespace == null), pass null as namespace URI and use tagName accordingly.
        // Also, for unprefixed tags, we want to pass null as namespace
        String namespace = null;
        if (prefix != null) {
            namespace = namespacesStack.peek().get(prefix);
            if (namespace == null) {
                // Treat as no namespace (null),
                // so namespace remains null
            }
        }

        String tagName = sourceEl.tagName();

        Element el;
        if (namespace == null) {
            // Create element without namespace to avoid NAMESPACE_ERR
            el = doc.createElement(tagName);
        } else {
            // Namespace declared - create with ns
            el = doc.createElementNS(namespace, tagName);
        }

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
