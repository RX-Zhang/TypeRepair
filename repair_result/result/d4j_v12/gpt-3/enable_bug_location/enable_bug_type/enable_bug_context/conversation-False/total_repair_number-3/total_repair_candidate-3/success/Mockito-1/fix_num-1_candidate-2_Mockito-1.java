public void captureArgumentsFrom(Invocation invocation) {
    if (invocation.getRawArguments() == null || invocation.getRawArguments().length == 0) {
        // Handle the case where there are no arguments
        return;
    }
    
    if (invocation.getMethod().isVarArgs()) {
        int indexOfVararg = invocation.getRawArguments().length - 1;
        // Add proper handling instead of throwing an exception
        if (invocation.getRawArguments()[indexOfVararg] == null) {
            return; // No varargs to capture
        }
        Object array = invocation.getRawArguments()[indexOfVararg];
        for (int i = 0; i < Array.getLength(array); i++) {
            for (Matcher matcher : matchers) {
                if (matcher instanceof CapturesArguments) {
                    ((CapturesArguments) matcher).captureFrom(Array.get(array, i));
                }
            }
        }
        return; // Early return after processing varargs
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
