public void captureArgumentsFrom(Invocation i) {
    Object[] arguments = i.getArguments();
    int k = 0;
    for (Matcher m : matchers) {
        if (m instanceof CapturesArguments) {
            if (k < arguments.length) { // Boundary check
                ((CapturesArguments) m).captureFrom(arguments[k]);
            } else {
                // Handle the case where there are not enough arguments
                break; // or you could log a warning if necessary
            }
        }
        k++;
    }
}
