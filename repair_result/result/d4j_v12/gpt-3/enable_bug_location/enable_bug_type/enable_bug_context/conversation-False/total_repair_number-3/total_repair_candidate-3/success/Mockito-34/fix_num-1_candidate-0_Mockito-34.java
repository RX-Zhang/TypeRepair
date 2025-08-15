public void captureArgumentsFrom(Invocation i) {
    int k = 0;
    Object[] arguments = i.getArguments();
    for (Matcher m : matchers) {
        if (m instanceof CapturesArguments) {
            // Check if the index k is within the bounds of the arguments array
            if (k < arguments.length) {
                ((CapturesArguments) m).captureFrom(arguments[k]);
            } else {
                // Handle the case where there are not enough arguments
                // You may choose to log this or handle it as needed
                break; // Exit the loop if there are no more arguments to capture
            }
        }
        k++;
    }
}
