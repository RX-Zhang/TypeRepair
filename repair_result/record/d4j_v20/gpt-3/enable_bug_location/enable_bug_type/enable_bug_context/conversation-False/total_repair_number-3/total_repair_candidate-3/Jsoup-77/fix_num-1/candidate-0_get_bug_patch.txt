private void popStackToClose(Token.EndTag endTag) {
    String elName = endTag.name();
    Element firstFound = null;

    // Find the matching element in the stack
    for (int pos = stack.size() - 1; pos >= 0; pos--) {
        Element next = stack.get(pos);
        if (next.nodeName().equals(elName)) {
            firstFound = next;
            break;
        }
    }
    if (firstFound == null)
        return; // not found, skip

    // Pop elements until the matching element is removed
    for (int pos = stack.size() - 1; pos >= 0; pos--) {
        Element next = stack.get(pos);
        stack.remove(pos);
        if (next == firstFound)
            break;
    }
}
