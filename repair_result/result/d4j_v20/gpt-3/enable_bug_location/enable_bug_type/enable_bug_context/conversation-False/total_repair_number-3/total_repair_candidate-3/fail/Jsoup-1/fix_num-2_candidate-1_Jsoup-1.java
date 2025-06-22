private void normalise(Element element) {
    List<Node> toMove = new ArrayList<Node>();
    for (Node node: element.childNodes()) { // Fixed method call to childNodes()
        if (node instanceof TextNode) {
            TextNode tn = (TextNode) node;
            if (!tn.isBlank())
                toMove.add(tn);
        }
    }

    for (Node node: toMove) {
        element.removeChild(node);
        body().appendChild(new TextNode(" ", "")); // This line was incorrectly adding a space before the text node
        body().appendChild(node);
    }
}
