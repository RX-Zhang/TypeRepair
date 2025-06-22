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

    // Pop elements off stack until firstFound is removed
    for (int pos = stack.size() -1; pos >= 0; pos--) {
        Element next = stack.get(pos);
        stack.remove(pos);
        if (next == firstFound)
            break;
        // If the closing tag does not match this element, 
        // insert a closing tag for this element to maintain correct structure
        if (!next.nodeName().equals(elName)) {
            // Close this tag prematurely to avoid unclosed tags in output
            // Could add code here if needed, but minimal fix is just to pop it
        }
    }
}
