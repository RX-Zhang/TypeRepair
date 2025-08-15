private void popStackToClose(Token.EndTag endTag) {
    String elName = endTag.name();
    Element firstFound = null;

    for (int pos = stack.size() -1; pos >= 0; pos--) {
        Element next = stack.get(pos);
        if (next.nodeName().equals(elName)) {
            firstFound = next;
            break;
        }
    }
    if (firstFound == null)
        return; // not found, skip

    // Only pop elements above and including firstFound,
    // but do not pop elements below firstFound.
    for (int pos = stack.size() -1; pos >= 0; pos--) {
        Element next = stack.get(pos);
        stack.remove(pos);
        if (next == firstFound)
            break;
    }
}
