public static boolean isJSIdentifier(String s) {
  int length = s.length();

  if (length == 0 ||
      !isJSIdentifierStart(s.charAt(0))) {
    return false;
  }

  for (int i = 1; i < length; i++) {
    if (!isJSIdentifierPart(s.charAt(i))) {
      return false;
    }
  }

  return true;
}

private static boolean isJSIdentifierStart(char c) {
  // JavaScript identifiers can start with Unicode letters, $, or _
  return (c == '$') || (c == '_') || Character.isLetter(c);
}

private static boolean isJSIdentifierPart(char c) {
  // JavaScript identifier part can be letters, digits, $, _, and some unicode combining marks
  return (c == '$') || (c == '_') || Character.isLetterOrDigit(c) || Character.getType(c) == Character.CONNECTOR_PUNCTUATION;
}
