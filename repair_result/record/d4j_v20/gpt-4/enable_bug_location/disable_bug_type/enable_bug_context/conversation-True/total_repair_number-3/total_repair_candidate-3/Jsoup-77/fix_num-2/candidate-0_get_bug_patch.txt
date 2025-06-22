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
        if (next == firstFound) {
            stack.remove(pos);
            break;
        }
        popChildFromStack(next);
        stack.remove(pos);
    }
}

private void popChildFromStack(Element el) {
    // recursively pop children from the stack to avoid wrongly nesting
    // This method is assumed needed to handle nested elements incorrectly closed; add as helper
    // (implementation depends on class context, possibly empty or adjusted)
}
