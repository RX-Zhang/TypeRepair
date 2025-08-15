public void captureArgumentsFrom(Invocation i) {
    int k = 0;
    Object[] arguments = i.getArguments();
    for (Matcher m : matchers) {
        if (m instanceof CapturesArguments) {
            if (k < arguments.length) { // Boundary check to prevent ArrayIndexOutOfBoundsException
                ((CapturesArguments) m).captureFrom(arguments[k]);
            } else {
                // Handle the case where there are not enough arguments
                // You may choose to log this or throw an exception based on your requirements
                break; // Exit the loop if there are no more arguments to capture
            }
        }
        k++;
    }
}
