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
    }

    Element body = body();
    for (int i = 0; i < toMove.size(); i++) {
        if (i > 0) {
            body.appendChild(new TextNode(" ", ""));
        }
        body.appendChild(toMove.get(i));
    }
}
