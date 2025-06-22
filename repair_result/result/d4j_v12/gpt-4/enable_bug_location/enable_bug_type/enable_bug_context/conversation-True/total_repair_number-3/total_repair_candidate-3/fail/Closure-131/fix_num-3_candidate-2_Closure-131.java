public static boolean isJSIdentifier(String s) {
  int length = s.length();

  if (length == 0 ||
      !Character.isJavaIdentifierStart(s.charAt(0)) ||
      s.charAt(0) <= 31) { // Control characters are not allowed
    return false;
  }

  for (int i = 1; i < length; i++) {
    char c = s.charAt(i);
    if (!Character.isJavaIdentifierPart(c) || c <= 31) { // Control characters are not allowed
      return false;
    }
  }

  return true;
}
