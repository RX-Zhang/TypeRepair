public void captureArgumentsFrom(Invocation i) {
    List<Matcher> matchers = getMatchers();
    Object[] arguments = i.getArguments();
    int k = 0;
    for (Matcher m : matchers) {
        if (m instanceof CapturesArguments) {
            // Safely capture argument only if index is valid
            if (k < arguments.length) {
                ((CapturesArguments) m).captureFrom(arguments[k]);
            } else {
                // No corresponding argument, so capture nothing or handle accordingly
                ((CapturesArguments) m).captureFrom(null);
            }
        }
        k++;
    }
}
