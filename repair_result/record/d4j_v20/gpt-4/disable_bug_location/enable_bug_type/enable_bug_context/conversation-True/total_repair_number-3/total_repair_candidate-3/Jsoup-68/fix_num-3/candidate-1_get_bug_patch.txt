private boolean inSpecificScope(String[] targetNames, String[] baseTypes, String[] extraTypes) {
    // https://html.spec.whatwg.org/multipage/parsing.html#has-an-element-in-the-specific-scope
    int stackSize = stack.size();
    if (stackSize == 0) {
        return false; // prevent empty stack access
    }

    int bottom = stackSize - 1;
    if (bottom > MaxScopeSearchDepth) {
        bottom = MaxScopeSearchDepth;
    }

    final int top = bottom > MaxScopeSearchDepth ? bottom - MaxScopeSearchDepth : 0;
    // don't walk too far up the tree

    for (int pos = bottom; pos >= top; pos--) {
        Element el = stack.get(pos);
        if (el == null) {
            continue; // safety check in case of null elements
        }
        final String elName = el.nodeName();
        if (inSorted(elName, targetNames))
            return true;
        if (inSorted(elName, baseTypes))
            return false;
        if (extraTypes != null && inSorted(elName, extraTypes))
            return false;
    }
    // Validate.fail("Should not be reachable"); // would end up false because hitting 'html' at root (basetypes)
    return false;
}
