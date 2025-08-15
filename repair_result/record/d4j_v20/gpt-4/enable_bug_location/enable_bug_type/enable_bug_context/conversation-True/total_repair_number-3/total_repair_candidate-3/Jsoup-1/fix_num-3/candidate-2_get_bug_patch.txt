private void normalise(Element element) {
    List<Node> toMove = new ArrayList<Node>();
    for (Node node : element.childNodes) {
        if (node instanceof TextNode) {
            TextNode tn = (TextNode) node;
            if (!tn.isBlank())
                toMove.add(tn);
        }
    }

    // Move text nodes in order, preserving their original relative order.
    for (Node node : toMove) {
        element.removeChild(node    }
    for (Node node : toMove) {
 body().appendChild(new TextNode(" ", ""));
        body().appendChild(node);
    }
}
