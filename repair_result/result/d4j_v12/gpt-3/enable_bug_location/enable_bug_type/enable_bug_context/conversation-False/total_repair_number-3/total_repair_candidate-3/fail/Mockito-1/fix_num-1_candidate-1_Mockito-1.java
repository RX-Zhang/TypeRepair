public void captureArgumentsFrom(Invocation invocation) {
    if (invocation.getRawArguments() == null || invocation.getRawArguments().length == 0) {
        throw new IllegalArgumentException("Invocation must have at least one argument.");
    }

    if (invocation.getMethod().isVarArgs()) {
        int indexOfVararg = invocation.getRawArguments().length - 1;
        Object vararg = invocation.getRawArguments()[indexOfVararg];
        if (vararg == null || !vararg.getClass().isArray()) {
            throw new UnsupportedOperationException("Varargs must be an array.");
        }
    } else {
        for (int position = 0; position < matchers.size(); position++) {
            Matcher m = matchers.get(position);
            if (m instanceof CapturesArguments) {
                if (position < invocation.getRawArguments().length) {
                    ((CapturesArguments) m).captureFrom(invocation.getArgumentAt(position, Object.class));
                } else {
                    throw new IndexOutOfBoundsException("No argument at position: " + position);
                }
            }
        }
    }

    for (int position = 0; position < matchers.size(); position++) {
        Matcher m = matchers.get(position);
        if (m instanceof CapturesArguments && position < invocation.getRawArguments().length) {
            if (isVariableArgument(invocation, position) && isVarargMatcher(m)) {
                Object array = invocation.getRawArguments()[position];
                for (int i = 0; i < Array.getLength(array); i++) {
                    ((CapturesArguments) m).captureFrom(Array.get(array, i));
                }
                return;
            } else {
                ((CapturesArguments) m).captureFrom(invocation.getRawArguments()[position]);
            }
        }
    }
}
