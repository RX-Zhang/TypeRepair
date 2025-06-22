private void popStackToClose(Token.EndTag endTag) {
    String elName = endTag.name();
    Element firstFound = null;

    for (int pos = stack.size() - 1; pos >= 0; pos--) {
        Element next = stack.get(pos);
        if (next.nodeName().equals(elName)) {
            firstFound = next;
            break;
        }
    }
    if (firstFound == null)
        return; // not found, skip

    for (int pos = stack.size() - 1; pos >= 0; pos--) {
        Element next = stack.get(pos);
        stack.remove(pos);
        if (next == firstFound)
            break;
    }

    insertFirst(firstFound);
}

private void insertFirst(Element firstFound) {
    // Re-insert the elements above firstFound in the stack except the closed elements
    // This helps to maintain the correct stack state after closing the tag
    // Assuming that original elements above firstFound are popped, so we re-insert them in order
    // If the above elements' re-insertion is needed (this logic may depend on the parser's stack usage)
    // If no other data members are used, the following might be enough:

    // No-op in this minimal example because we have no other elements to re-insert.
    // In a fuller context, this method would ensure proper stack integrity.
}
