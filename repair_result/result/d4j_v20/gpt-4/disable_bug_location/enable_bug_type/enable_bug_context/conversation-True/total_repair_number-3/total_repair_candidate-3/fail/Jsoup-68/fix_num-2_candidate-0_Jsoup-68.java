private boolean inSpecificScope(String[] targetNames, String[] baseTypes, String[] extraTypes) {
    // https://html.spec.whatwg.org/multipage/parsing.html#has-an-element-in-the-specific-scope
    int bottom = stack.size() - 1;
    if (bottom < 0) {
        // Stack is empty, so element cannot be in scope
        return false;
    }
    // Limit how far we search up the stack, avoid infinite loops and stack overflow
    if (bottom > MaxScopeSearchDepth) {
        bottom = MaxScopeSearchDepth;
    }
    final int top = bottom > MaxScopeSearchDepth ? bottom - MaxScopeSearchDepth : 0;

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
