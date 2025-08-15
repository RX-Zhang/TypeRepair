public void captureArgumentsFrom(Invocation invocation) {
    if (invocation.getRawArguments() == null) {
        throw new IllegalArgumentException("Invocation arguments cannot be null.");
    }

    if (invocation.getMethod().isVarArgs()) {
        int indexOfVararg = invocation.getRawArguments().length - 1;

        // Check if we're trying to access a valid index
        if (indexOfVararg < 0 || indexOfVararg >= invocation.getRawArguments().length) {
            throw new ArrayIndexOutOfBoundsException("Invalid index for varargs: " + indexOfVararg);
        }

        // Handle varargs specific logic here (removed UnsupportedOperationException)
        Object varargArray = invocation.getRawArguments()[indexOfVararg];
        if (varargArray != null && varargArray.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(varargArray); i++) {
                for (int position = 0; position < matchers.size(); position++) {
                    Matcher m = matchers.get(position);
                    if (m instanceof CapturesArguments) {
                        ((CapturesArguments) m).captureFrom(Array.get(varargArray, i));
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
