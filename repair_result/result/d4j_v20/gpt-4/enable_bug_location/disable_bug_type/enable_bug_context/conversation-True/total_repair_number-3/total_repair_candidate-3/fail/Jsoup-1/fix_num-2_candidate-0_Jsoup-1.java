private void normalise(Element element) {
    List<Node> toMove = new ArrayList<Node>();
    for (Node node: element.childNodes()) { // Fixed: Added parentheses to call childNodes() method
        if (node instanceof TextNode) {
            TextNode tn = (TextNode) node;
            if (!tn.isBlank())
                toMove.add(tn);
        }
    }

    for (Node node: toMove) {
        element.removeChild(node);
        body().appendChild(new TextNode(" ", "")); // Fixed: Added a space to maintain text order
        body().appendChild(node);
    }
}
