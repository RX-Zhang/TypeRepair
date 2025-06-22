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
        } else {
            Element removed = stack.remove(pos);
            // Reparent the children of removed element to the current top of the stack or to the parent of removed element 
            if (!removed.childNodes().isEmpty()) {
                Element insertTarget = stack.isEmpty() ? removed.parent() : stack.get(stack.size() -1);
                for (Node child : removed.childNodes()) {
                    child.setParentNode(insertTarget);
                    insertTarget.appendChild(child);
                }
            }
        }
    }
}
