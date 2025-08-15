private void normalise(Element element) {
    List<Node> toMove = new ArrayList<Node>();
    for (Node node: element.childNodes) {
        if (node instanceof TextNode) {
            TextNode tn = (TextNode) node;
            if (!tn.isBlank())
                toMove.add(tn);
        }
    }

    for (Node node: toMove) {
        element.removeChild(node);
        // Append node directly without adding extra space before each text node
        // To keep original ordering, append the node itself
        body().appendChild(node);
    }
}
