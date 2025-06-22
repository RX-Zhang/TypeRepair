private boolean inSpecificScope(String[] targetNames, String[] baseTypes, String[] extraTypes) {
    // https://html.spec.whatwg.org/multipage/parsing.html#has-an-element-in-the-specific-scope
    if (stack == null || stack.isEmpty()) {
        return false;
    }

    int bottom = stack.size() - 1;
    int top;

    if (bottom > MaxScopeSearchDepth) {
        top = bottom - MaxScopeSearchDepth;
        bottom = bottom;
    } else {
        top = 0;
    }

    // Ensure top is not negative
    if (top < 0) {
        top = 0;
    }

    try {
        for (int pos = bottom; pos >= top; pos--) {
            final String elName = stack.get(pos).nodeName();
            if (inSorted(elName, targetNames))
                return true;
            if (inSorted(elName, baseTypes))
                return false;
            if (extraTypes != null && inSorted(elName, extraTypes))
                return false;
        }
    } catch (IndexOutOfBoundsException ex) {
        // Defensive catch: stack size may change concurrently or unexpectedly
        return false;
    }

    return false;
}
