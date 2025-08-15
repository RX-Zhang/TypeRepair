public void head(org.jsoup.nodes.Node source, int depth) {
    namespacesStack.push(new HashMap<>(namespacesStack.peek())); // inherit from above on the stack
    if (source instanceof org.jsoup.nodes.Element) {
        org.jsoup.nodes.Element sourceEl = (org.jsoup.nodes.Element) source;

        String prefix = updateNamespaces(sourceEl);
        String namespace = namespacesStack.peek().get(prefix);
        String localName = sourceEl.tagName();

        // If namespace is null or empty and tagName contains ':', split it for localName and prefix
        if ((namespace == null || namespace.isEmpty()) && localName.contains(":")) {
            int colonIndex = localName.indexOf(':');
            String prefixPart = localName.substring(0, colonIndex);
            String localPart = localName.substring(colonIndex + 1);
            localName = localPart;
            // namespace remains null, which is allowed for local names without namespace
        }

        Element el;
        if (namespace == null || namespace.isEmpty()) {
            // Create element without namespace if namespace is not defined
            el = doc.createElement(localName);
        } else {
            // Create element with namespace if present
            el = doc.createElementNS(namespace, localName);
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
