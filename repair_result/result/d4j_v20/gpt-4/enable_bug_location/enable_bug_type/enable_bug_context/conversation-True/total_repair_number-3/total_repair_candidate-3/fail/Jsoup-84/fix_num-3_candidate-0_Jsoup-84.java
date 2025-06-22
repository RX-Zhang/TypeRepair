public void head(org.jsoup.nodes.Node source, int depth) {
    namespacesStack.push(new HashMap<>(namespacesStack.peek())); // inherit from above on the stack
    if (source instanceof org.jsoup.nodes.Element) {
        org.jsoup.nodes.Element sourceEl = (org.jsoup.nodes.Element) source;

        String prefix = updateNamespaces(sourceEl);
        String namespace = namespacesStack.peek().get(prefix);

        String tagName = sourceEl.tagName();
        String localName = tagName;
        int colonIndex = tagName.indexOf(':');
        if (colonIndex != -1) {
            localName = tagName.substring(colonIndex + 1);
            // if namespace is null (undeclared), createElementNS should be called with null namespace
            if (namespace == null) {
                namespace = null;
            }
        } else {
            localName = tagName;
        }

        Element el;
        if (namespace == null) {
            el = doc.createElement(localName);
        } else {
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
