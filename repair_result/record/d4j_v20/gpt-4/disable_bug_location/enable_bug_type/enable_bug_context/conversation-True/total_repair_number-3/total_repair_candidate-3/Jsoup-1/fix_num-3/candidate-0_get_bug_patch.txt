private void normalise(Element element) {
    List<Node> toMove = new ArrayList<Node>();
    for (Node node : element.childNodes()) {
        if (node instanceof TextNode) {
            TextNode tn = (TextNode) node;
            if (!tn.isBlank())
                toMove.add(tn);
        }
    }

    for (Node node : toMove) {
        element.removeChild(node);
        if (body() != null) {
            // Remove trailing whitespace if not first node to avoid extra leading space
            if (body().childNodeSize() > 0 && !(node instanceof TextNode && ((TextNode) node).isBlank())) {
                body().appendChild(new TextNode(" ", ""));
            }
            body().appendChild(node);
        }
    }
}
