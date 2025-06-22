public static boolean isJSIdentifier(String s) {
  int length = s.length();

  if (length == 0 ||
      !Character.isJavaIdentifierStart(s.charAt(0))) {
    return false;
  }

  for (int i = 1; i < length; i++) {
    char c = s.charAt(i);
    // Control characters (like \u0004) are valid in Java identifiers but not in JS identifiers
    // So we must explicitly reject control characters
    if (!Character.isJavaIdentifierPart(c) || Character.isISOControl(c)) {
      return false;
    }
  }

  return true;
}
