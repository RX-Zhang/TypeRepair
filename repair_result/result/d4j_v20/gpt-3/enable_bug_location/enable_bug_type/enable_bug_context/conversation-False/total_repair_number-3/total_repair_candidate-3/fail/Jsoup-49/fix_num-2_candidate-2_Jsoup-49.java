protected void addChildren(int index, Node... children) {
    Validate.noNullElements(children);
    ensureChildNodes();
    for (int i = 0; i < children.length; i++) { // Fixed the loop to iterate from 0 to length
        Node in = children[i];
        reparentChild(in);
        childNodes.add(index + i, in); // Adjusted index to insert children correctly
    }
    reindexChildren(index);
}
