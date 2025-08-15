private void normalise(Element element) {
    List<Node> toMove = new ArrayList<Node>();
    for (Node node : element.childNodes) {
        if (node instanceof TextNode) {
            TextNode tn = (TextNode) node;
            if (!tn.isBlank())
                toMove.add(tn);
        }
    }

    for (Node node : toMove) {
        element.removeChild(node);
        // Only add space if the last appended node is not a space text node to avoid leading spaces
        List<Node> bodyChildren = body().childNodes();
        if (!bodyChildren.isEmpty()) {
            Node last = bodyChildren.get(bodyChildren.size() - 1);
            if (!(last instanceof TextNode && ((TextNode) last).isBlank()))
                body().appendChild(new TextNode(" ", ""));
        }
        body().appendChild(node);
    }
}
