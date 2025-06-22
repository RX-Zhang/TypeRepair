protected void addChildren(int index, Node... children) {
    Validate.noNullElements(children);
    ensureChildNodes();
    // Add children in normal order, not reverse, to maintain order after insertion
    for (int i = 0; i < children.length; i++) {
        Node in = children[i];
        reparentChild(in);
        childNodes.add(index + i, in);
    }
    reindexChildren(index);
}
