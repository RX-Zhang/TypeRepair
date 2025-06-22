private boolean inSpecificScope(String[] targetNames, String[] baseTypes, String[] extraTypes) {
    // https://html.spec.whatwg.org/multipage/parsing.html#has-an-element-in-the-specific-scope
    if (stack == null || stack.isEmpty()) {
        return false; // Handle empty stack scenario
    }

    int bottom = stack.size() - 1;
    if (bottom > MaxScopeSearchDepth) {
        bottom = MaxScopeSearchDepth;
    }
    final int top = bottom > MaxScopeSearchDepth ? bottom - MaxScopeSearchDepth : 0;
    // don't walk too far up the tree

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
