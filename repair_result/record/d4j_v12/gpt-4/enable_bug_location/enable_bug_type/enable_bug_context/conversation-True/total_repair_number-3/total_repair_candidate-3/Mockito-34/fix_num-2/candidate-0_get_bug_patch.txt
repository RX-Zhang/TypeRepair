public void captureArgumentsFrom(Invocation i) {
    Object[] actualArgs = i.getArguments();
    for (int k = 0; k < matchers.size(); k++) {
        Matcher m = (Matcher) matchers.get(k);
        if (m instanceof CapturesArguments && k < actualArgs.length) {
            ((CapturesArguments) m).captureFrom(actualArgs[k]);
        }
    }
}
