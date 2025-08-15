public void captureArgumentsFrom(Invocation i) {
    int k = 0;
    Object[] arguments = i.getArguments();
    
    if (arguments == null) {
        return; // Handle null arguments gracefully
    }

    for (Matcher m : matchers) {
        if (m instanceof CapturesArguments) {
            if (k < arguments.length) { // Check for index bounds
                ((CapturesArguments) m).captureFrom(arguments[k]);
            } else {
                break; // Exit if there are no more arguments to capture
            }
        }
        k++;
    }
}
