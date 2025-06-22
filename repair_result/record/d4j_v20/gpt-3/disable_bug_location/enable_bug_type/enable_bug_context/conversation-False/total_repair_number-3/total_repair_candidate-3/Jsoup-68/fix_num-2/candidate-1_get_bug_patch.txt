private boolean inSpecificScope(String[] targetNames, String[] baseTypes, String[] extraTypes) {
    // https://html.spec.whatwg.org/multipage/parsing.html#has-an-element-in-the-specific-scope
    int stackSize = stack.size();
    int bottom = stackSize - 1;
    if (bottom > MaxScopeSearchDepth) {
        bottom = MaxScopeSearchDepth;
    }
    final int top = 0;  // Always starts from 0

    // don't walk too far up the tree
    // Scan from bottom (closest to top of stack) up to top (0)
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
