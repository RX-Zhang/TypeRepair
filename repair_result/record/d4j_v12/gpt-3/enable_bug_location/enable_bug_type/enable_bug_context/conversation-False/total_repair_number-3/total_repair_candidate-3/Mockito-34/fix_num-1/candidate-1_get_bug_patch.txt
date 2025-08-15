public void captureArgumentsFrom(Invocation i) {
    int k = 0;
    Object[] arguments = i.getArguments();
    for (Matcher m : matchers) {
        if (m instanceof CapturesArguments) {
            // Check if the index is within bounds before accessing the arguments array
            if (k < arguments.length) {
                ((CapturesArguments) m).captureFrom(arguments[k]);
            } else {
                // Handle the case where there are not enough arguments
                break; // or you could log a warning if needed
            }
        }
        k++;
    }
}
