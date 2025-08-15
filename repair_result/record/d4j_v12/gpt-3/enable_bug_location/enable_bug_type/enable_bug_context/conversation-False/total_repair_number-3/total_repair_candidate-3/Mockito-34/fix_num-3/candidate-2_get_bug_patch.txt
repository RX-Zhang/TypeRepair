public void captureArgumentsFrom(Invocation i) {
    int k = 0;
    Object[] arguments = i.getArguments();
    for (Matcher m : matchers) {
        if (m instanceof CapturesArguments) {
            if (k < arguments.length) { // Boundary check added
                ((CapturesArguments) m).captureFrom(arguments[k]);
            }
        }
        k++;
    }
}
