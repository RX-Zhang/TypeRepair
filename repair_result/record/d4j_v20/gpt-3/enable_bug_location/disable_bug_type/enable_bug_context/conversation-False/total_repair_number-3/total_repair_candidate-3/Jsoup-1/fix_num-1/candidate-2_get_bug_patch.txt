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
        int index = element.childNodes.indexOf(node);
        element.removeChild(node);

        // If the node is not the last child, preserve spacing by inserting a space TextNode only if the node after removed node is not a TextNode starting with space
        if (index < element.childNodes.size()) {
            TextNode spaceNode = new TextNode(" ", "");
            body().insertChild(body().childNodeSize() > 0 ? body().childNodeSize() : 0, spaceNode);
        }
        body().appendChild(node);
    }
}
