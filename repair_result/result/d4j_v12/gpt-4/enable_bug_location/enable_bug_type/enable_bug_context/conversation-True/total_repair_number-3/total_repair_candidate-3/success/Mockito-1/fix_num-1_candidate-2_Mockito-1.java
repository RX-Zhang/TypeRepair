public void captureArgumentsFrom(Invocation invocation) {
    if (invocation.getMethod().isVarArgs()) {
        int indexOfVararg = invocation.getRawArguments().length - 1;

        // Support for capturing varargs arguments using matchers
        for (int position = 0; position < matchers.size(); position++) {
            Matcher m = matchers.get(position);
            if (m instanceof CapturesArguments && invocation.getRawArguments().length > position) {
                if (isVariableArgument(invocation, position) && isVarargMatcher(m)) {
                    Object array = invocation.getRawArguments()[position];
                    if (array != null && array.getClass().isArray()) {
                        for (int i = 0; i < Array.getLength(array); i++) {
                            ((CapturesArguments) m).captureFrom(Array.get(array, i));
                        }
                    } else {
                        ((CapturesArguments) m).captureFrom(array);
                    }
                    // Since all varargs handled, no need to continue
                    return;
                } else {
                    ((CapturesArguments) m).captureFrom(invocation.getRawArguments()[position]);
                }
            }
        }
    } else {
        for (int position = 0; position < matchers.size(); position++) {
            Matcher m = matchers.get(position);
            if (m instanceof CapturesArguments) {
                ((CapturesArguments) m).captureFrom(invocation.getArgumentAt(position, Object.class));
            }
        }
    }
}
