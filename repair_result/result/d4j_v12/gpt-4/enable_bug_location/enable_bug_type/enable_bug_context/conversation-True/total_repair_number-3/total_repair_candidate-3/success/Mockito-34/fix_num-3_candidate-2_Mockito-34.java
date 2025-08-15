public void captureArgumentsFrom(Invocation i) {
    Object[] args = i.getArguments();
    List<Matcher> matchers = getMatchers();
    for (int k = 0; k < matchers.size(); k++) {
        Matcher m = matchers.get(k);
        if (m instanceof CapturesArguments && k < args.length) {
            ((CapturesArguments) m).captureFrom(args[k]);
        }
    }
}
