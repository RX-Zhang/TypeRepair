private boolean inSpecificScope(String[] targetNames, String[] baseTypes, String[] extraTypes) {
    // https://html.spec.whatwg.org/multipage/parsing.html#has-an-element-in-the-specific-scope
    int stackSize = stack.size();
    if (stackSize == 0) {
        return false; // no elements on stack, can't be in scope
    }
    int bottom = stackSize - 1;
    int top;
    if (bottom > MaxScopeSearchDepth) {
        // Restrict how far up the stack to search to avoid deep recursion and StackOverflowError
        bottom = MaxScopeSearchDepth;
        top = 0;
    } else {
        top = bottom - MaxScopeSearchDepth;
        if (top < 0) {
            top = 0;
        }
    }

    // Walk up the stack from bottom to top, checking scope
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
