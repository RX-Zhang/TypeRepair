private int compareNodePointers(
    NodePointer p1,
    int depth1,
    NodePointer p2,
    int depth2) 
{
    if (depth1 < depth2) {
        int r = compareNodePointers(p1, depth1, p2.parent, depth2 - 1);
        return r == 0 ? -1 : r;
    }
    if (depth1 > depth2) {
        int r = compareNodePointers(p1.parent, depth1 - 1, p2, depth2);
        return r == 0 ? 1 : r;
    }
    if (p1 == null && p2 == null) {
        return 0;
    }

    if (p1 != null && p1.equals(p2)) {
        return 0;
    }

    // Check if both pointers belong to the same root node before proceeding
    if (p1.getRootNode() != p2.getRootNode()) {
        throw new JXPathException(
                "Cannot compare pointers that do not belong to the same tree: '"
                        + p1 + "' and '" + p2 + "'");
    }

    if (depth1 == 1) {
        // this condition can now be removed or keep it as a secondary check
        // but main check is above
        int r = p1.parent.compareChildNodePointers(p1, p2);
        return r;
    }
    int r = compareNodePointers(p1.parent, depth1 - 1, p2.parent, depth2 - 1);
    if (r != 0) {
        return r;
    }

    return p1.parent.compareChildNodePointers(p1, p2);
}
