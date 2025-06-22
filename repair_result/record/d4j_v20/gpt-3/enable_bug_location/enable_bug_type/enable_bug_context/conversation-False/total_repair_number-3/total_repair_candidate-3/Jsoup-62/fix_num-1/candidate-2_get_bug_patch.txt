boolean anyOtherEndTag(Token t, HtmlTreeBuilder tb) {
    String name = t.asEndTag().normalName();
    ArrayList<Element> stack = tb.getStack();
    if (stack == null || stack.isEmpty()) { // Boundary check
        tb.error(this);
        return false;
    }
    
    for (int pos = stack.size() - 1; pos >= 0; pos--) {
        Element node = stack.get(pos);
        if (node.nodeName().equals(name)) {
            tb.generateImpliedEndTags(name);
            if (!name.equals(tb.currentElement().nodeName())) {
                tb.error(this);
                return false; // Ensure logical flow is correct
            }
            tb.popStackToClose(name);
            break;
        } else {
            if (tb.isSpecial(node)) {
                tb.error(this);
                return false;
            }
        }
    }
    return true;
}
