public void captureArgumentsFrom(Invocation invocation) {
    if (invocation.getMethod().isVarArgs()) {
        int indexOfVararg = invocation.getRawArguments().length - 1;
        // Handle vararg case
        if (invocation.getRawArguments().length > indexOfVararg && invocation.getRawArguments()[indexOfVararg] != null) {
            Object array = invocation.getRawArguments()[indexOfVararg];
            for (int i = 0; i < Array.getLength(array); i++) {
                for (Matcher matcher : matchers) {
                    if (matcher instanceof CapturesArguments) {
                        ((CapturesArguments) matcher).captureFrom(Array.get(array, i));
                    }
                }
            }
        }
    } else {
        for (int position = 0; position < matchers.size(); position++) {
            Matcher m = matchers.get(position);
            if (m instanceof CapturesArguments) {
                if (invocation.getRawArguments().length > position) {
                    ((CapturesArguments) m).captureFrom(invocation.getArgumentAt(position, Object.class));
                }
            }
        }
    }
}
