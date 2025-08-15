private boolean toStringEquals(Matcher m, Object arg) {
    if (arg == null) {
        return false; // Handle null argument to prevent NullPointerException
    }
    return StringDescription.toString(m).equals(arg.toString());
}
