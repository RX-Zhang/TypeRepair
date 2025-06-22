private boolean toStringEquals(Matcher m, Object arg) {
    if (arg == null) {
        return false; // Handle the case where arg is null
    }
    return StringDescription.toString(m).equals(arg.toString());
}
