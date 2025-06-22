private void popStackToClose(Token.EndTag endTag) {
    String elName = endTag.name();
    Element firstFound = null;

    for (int pos = stack.size() - 1; pos >= 0; pos--) {
        Element next = stack.get(pos);
        if (next.nodeName().equals(elName)) {
            firstFound = next;
            // According to XML parsing rules, end tag should close the most recently opened matching tag.
            // So break here as we found the closest match from top of stack.
            break;
        }
    }
    if (firstFound == null)
        return; // not found, skip
    
    // Pop elements from stack until we removed the firstFound element
    for (int pos = stack.size() - 1; pos >= 0; pos--) {
        Element next = stack.get(pos);
        stack.remove(pos);
        if (next == firstFound)
            break;
    }
}
