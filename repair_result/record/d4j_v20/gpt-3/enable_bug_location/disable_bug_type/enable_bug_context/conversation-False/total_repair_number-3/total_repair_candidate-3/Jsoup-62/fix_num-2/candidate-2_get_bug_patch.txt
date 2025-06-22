boolean anyOtherEndTag(Token t, HtmlTreeBuilder tb) {
    String name = t.asEndTag().normalName();
    ArrayList<Element> stack = tb.getStack();
    for (int pos = stack.size() -1; pos >= 0; pos--) {
        Element node = stack.get(pos);
        if (node.nodeName().equals(name)) {
            tb.generateImpliedEndTags(name);
            if (!name.equals(tb.currentElement().nodeName()))
                tb.error(this);
            tb.popStackToClose(name);
            return true; // must return true here to avoid further processing
        } else {
            if (tb.isSpecial(node)) {
                tb.error(this);
                return false;
            }
        }
    }
    return false; // if no matching element found, should return false
}
