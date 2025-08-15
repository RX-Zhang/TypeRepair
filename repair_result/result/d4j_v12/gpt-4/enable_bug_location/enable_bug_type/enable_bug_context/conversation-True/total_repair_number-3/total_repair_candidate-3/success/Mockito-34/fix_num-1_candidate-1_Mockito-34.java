public void captureArgumentsFrom(Invocation i) {
    Object[] args = i.getArguments();
    List<Matcher> matchersList = getMatchers();
    int k = 0;
    for (Matcher m : matchersList) {
        if (m instanceof CapturesArguments) {
            if (k < args.length) {
                ((CapturesArguments) m).captureFrom(args[k]);
            }
        }
        k++;
    }
}
