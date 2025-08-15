boolean anyOtherEndTag(Token t, HtmlTreeBuilder tb) {
    String name = t.asEndTag().normalName();
    ArrayList<Element> stack = tb.getStack();
    for (int pos = stack.size() -1; pos >= 0; pos--) {
        Element node = stack.get(pos);
        if (node.nodeName().equalsIgnoreCase(name)) {
            tb.generateImpliedEndTags(name);
            if (!name.equalsIgnoreCase(tb.currentElement().nodeName()))
                tb.error(this);
            tb.popStackToClose(node.nodeName());
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
