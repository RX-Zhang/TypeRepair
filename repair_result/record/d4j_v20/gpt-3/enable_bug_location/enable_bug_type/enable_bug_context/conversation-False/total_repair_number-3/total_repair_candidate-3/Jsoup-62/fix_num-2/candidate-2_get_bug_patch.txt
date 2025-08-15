boolean anyOtherEndTag(Token t, HtmlTreeBuilder tb) {
    String name = t.asEndTag().normalName();
    ArrayList<Element> stack = tb.getStack();
    boolean foundMatchingNode = false; // Track if we find a matching node
    for (int pos = stack.size() - 1; pos >= 0; pos--) {
        Element node = stack.get(pos);
        if (node.nodeName().equals(name)) {
            foundMatchingNode = true; // We found a matching node
            tb.generateImpliedEndTags(name);
            if (!name.equals(tb.currentElement().nodeName())) {
                tb.error(this);
            }
            tb.popStackToClose(name);
            break;
        } else if (tb.isSpecial(node)) {
            tb.error(this);
            return false; // Return false if a special node is encountered
        }
    }
    return foundMatchingNode; // Return true only if a matching node was found
}
