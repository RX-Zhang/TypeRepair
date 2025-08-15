private void normalise(Element element) {
    List<Node> toMove = new ArrayList<Node>();
    for (Node node: new ArrayList<>(element.childNodes)) {
        if (node instanceof TextNode) {
            TextNode tn = (TextNode) node;
            if (!tn.isBlank())
                toMove.add(tn);
        }
    }

    for (Node node: toMove) {
        element.removeChild(node);
        // Only add a space if the last appended node is not already a space to avoid extra spaces
        List<Node> bodyChildren = body().childNodes;
        if (bodyChildren.isEmpty() || !(bodyChildren.get(bodyChildren.size() - 1) instanceof TextNode 
            && ((TextNode) bodyChildren.get(bodyChildren.size() - 1)).text().equals(" "))) {
            body().appendChild(new TextNode(" ", ""));
        }
        body().appendChild(node);
    }
}
