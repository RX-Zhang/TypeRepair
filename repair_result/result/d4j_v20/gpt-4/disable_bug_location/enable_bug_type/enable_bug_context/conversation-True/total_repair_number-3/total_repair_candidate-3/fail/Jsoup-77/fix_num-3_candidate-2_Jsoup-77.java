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

    for (int pos = stack.size() -1; pos >= 0; pos--) {
        Element next = stack.get(pos);
        stack.remove(pos);
        if (next == firstFound) {
            break;
        }
    }
    // After popping the elements, re-add the current element to the stack
    // or insert the firstFound back if necessary to maintain valid state as per HTML tree builder approach.
    // But for XML, as all tags should be well-formed, no re-adding is needed here.
}
