public static boolean isJSIdentifier(String s) {
  int length = s.length();

  if (length == 0 ||
      !Character.isJavaIdentifierStart(s.charAt(0))) {
    return false;
  }

  for (int i = 1; i < length; i++) {
    if (!Character.isJavaIdentifierPart(s.charAt(i)) || 
        s.charAt(i) == '\u0004') { // Exclude control characters like '\u0004'
      return false;
    }
  }

  return true;
}
