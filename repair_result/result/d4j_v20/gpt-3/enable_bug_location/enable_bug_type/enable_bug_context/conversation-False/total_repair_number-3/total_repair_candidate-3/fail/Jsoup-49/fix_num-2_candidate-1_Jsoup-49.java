protected void addChildren(int index, Node... children) {
    Validate.noNullElements(children);
    ensureChildNodes();
    for (int i = 0; i < children.length; i++) { // Changed to iterate from 0 to length
        Node in = children[i];
        reparentChild(in);
        childNodes.add(index + i, in); // Adjusted index to add children correctly
    }
    reindexChildren(index);
}
