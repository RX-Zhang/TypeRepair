public static boolean isJSIdentifier(String s) {
  int length = s.length();

  if (length == 0 ||
      !Character.isJavaIdentifierStart(s.charAt(0))) {
    return false;
  }

  for (int i = 1; i < length; i++) {
    // Reject ignorable control characters (like \u0004) explicitly
    char c = s.charAt(i);
    if (!Character.isJavaIdentifierPart(c) || Character.getType(c) == Character.CONTROL) {
      return false;
    }
  }

  return true;
}
