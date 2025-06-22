private int compareNodePointers(
    NodePointer p1,
    int depth1,
    NodePointer p2,
    int depth2) 
{
    if (depth1 < depth2) {
        int r = compareNodePointers(p1, depth1, p2.getParent(), depth2 - 1);
        return r == 0 ? -1 : r;
    }
    if (depth1 > depth2) {
        int r = compareNodePointers(p1.getParent(), depth1 - 1, p2, depth2);
        return r == 0 ? 1 : r;
    }
    if (p1 == null && p2 == null) {
        return 0;
    }

    if (p1 != null && p1.equals(p2)) {
        return 0;
    }

    if (depth1 == 1) {
        // Compare root nodes to check if they belong to the same tree
        Object root1 = p1 != null ? p1.getRootNode() : null;
        Object root2 = p2 != null ? p2.getRootNode() : null;
        if (root1 == null || root2 == null || !root1.equals(root2)) {
            throw new JXPathException(
                "Cannot compare pointers that do not belong to the same tree: '"
                        + p1 + "' and '" + p2 + "'");
        }
    }
    int r = compareNodePointers(p1.getParent(), depth1 - 1, p2.getParent(), depth2 - 1);
    if (r != 0) {
        return r;
    }

    return p1.compareChildNodePointers(p1, p2);
}
