public void captureArgumentsFrom(Invocation i) {
    int k = 0;
    Object[] arguments = i.getArguments();
    for (Matcher m : matchers) {
        if (m instanceof CapturesArguments) {
            // Check if the index k is within bounds of the arguments array
            if (k < arguments.length) {
                ((CapturesArguments) m).captureFrom(arguments[k]);
            } else {
                // Optionally handle the case where there are not enough arguments
                // e.g., log a warning or throw an exception
            }
        }
        k++;
    }
}
