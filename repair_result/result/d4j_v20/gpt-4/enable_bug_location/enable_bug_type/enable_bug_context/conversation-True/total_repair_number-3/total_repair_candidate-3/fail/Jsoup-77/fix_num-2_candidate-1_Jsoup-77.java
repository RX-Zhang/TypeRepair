private void popStackToClose(Token.EndTag endTag) {
    String elName = endTag.name();
    Element firstFound = null;

    // Find the first element on the stack that matches the tag name to close
    for (int pos = stack.size() -1; pos >= 0; pos--) {
        Element next = stack.get(pos);
        if (next.nodeName().equals(elName)) {
            firstFound = next;
            break;
        }
    }
    if (firstFound == null)
        return; // not found, skip

    // Pop the stack until the matching element is removed
    for (int pos = stack.size() -1; pos >= 0; pos--) {
        Element next = stack.get(pos);
        stack.remove(pos);
        if (next == firstFound)
            break;
    }
}
