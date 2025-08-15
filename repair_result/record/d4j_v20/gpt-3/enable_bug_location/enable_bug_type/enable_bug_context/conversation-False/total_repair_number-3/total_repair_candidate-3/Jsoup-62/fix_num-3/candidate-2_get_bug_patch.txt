boolean anyOtherEndTag(Token t, HtmlTreeBuilder tb) {
    String name = t.asEndTag().normalName();
    ArrayList<Element> stack = tb.getStack();
    boolean foundMatchingTag = false; // Track if a matching tag is found
    for (int pos = stack.size() - 1; pos >= 0; pos--) {
        Element node = stack.get(pos);
        if (node.nodeName().equals(name)) {
            foundMatchingTag = true; // Match found
            tb.generateImpliedEndTags(name);
            if (!name.equals(tb.currentElement().nodeName())) {
                tb.error(this);
            }
            tb.popStackToClose(name);
            break;
        } else if (tb.isSpecial(node)) {
            tb.error(this);
            return false;
        }
    }
    return foundMatchingTag; // Return true only if a matching tag was found
}
