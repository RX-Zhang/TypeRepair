public void captureArgumentsFrom(Invocation invocation) {
    if (invocation.getMethod().isVarArgs()) {
        int indexOfVararg = invocation.getRawArguments().length - 1;
        if (indexOfVararg < 0 || invocation.getRawArguments()[indexOfVararg] == null) {
            throw new UnsupportedOperationException("Varargs cannot be captured from an empty or null argument.");
        }
        // Handle varargs capturing logic here
        Object array = invocation.getRawArguments()[indexOfVararg];
        if (array instanceof Object[]) {
            for (Object arg : (Object[]) array) {
                for (Matcher m : matchers) {
                    if (m instanceof CapturesArguments) {
                        ((CapturesArguments) m).captureFrom(arg);
                    }
                }
            }
        }
    } else {
        for (int position = 0; position < matchers.size(); position++) {
            Matcher m = matchers.get(position);
            if (m instanceof CapturesArguments) {
                if (position < invocation.getRawArguments().length) {
                    ((CapturesArguments) m).captureFrom(invocation.getArgumentAt(position, Object.class));
                }
            }
        }
    }
}
