private boolean inSpecificScope(String[] targetNames, String[] baseTypes, String[] extraTypes) {
    // https://html.spec.whatwg.org/multipage/parsing.html#has-an-element-in-the-specific-scope
    int bottom = stack.size() - 1;
    if (bottom > MaxScopeSearchDepth) {
        bottom = MaxScopeSearchDepth;
    }
    final int top = 0; // always start at the bottom of the stack or MaxScopeSearchDepth, no offset

    for (int pos = bottom; pos >= top; pos--) {
        final String elName = stack.get(pos).nodeName();
        if (inSorted(elName, targetNames))
            return true;
        if (inSorted(elName, baseTypes))
            return false;
        if (extraTypes != null && inSorted(elName, extraTypes))
            return false;
    }
    return false;
}
