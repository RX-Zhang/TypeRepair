public void captureArgumentsFrom(Invocation i) {
    Object[] args = i.getArguments();
    for (int k = 0; k < matchers.size(); k++) {
        Matcher m = (Matcher) matchers.get(k);
        if (m instanceof CapturesArguments) {
            if (k < args.length) {
                ((CapturesArguments) m).captureFrom(args[k]);
            }
        }
    }
}
