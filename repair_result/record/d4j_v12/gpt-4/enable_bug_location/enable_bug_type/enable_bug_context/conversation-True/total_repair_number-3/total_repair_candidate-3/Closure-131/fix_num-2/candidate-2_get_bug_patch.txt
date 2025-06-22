public static boolean isJSIdentifier(String s) {
  int length = s.length();

  if (length == 0) {
    return false;
  }

  // According to ECMAScript spec, identifiers can only contain certain Unicode characters.
  // Java's Character.isJavaIdentifierStart and isJavaIdentifierPart allow some characters
  // that are invalid in JS identifiers (e.g., control characters).
  // We need to restrict to ASCII letters, digits, $, _, and Unicode identifier characters
  // excluding control characters (e.g., exclude characters with code points < 32 or 127-159).

  char firstChar = s.charAt(0);
  if (!isValidJSIdentifierStartChar(firstChar)) {
    return false;
  }

  for (int i = 1; i < length; i++) {
    char ch = s.charAt(i);
    if (!isValidJSIdentifierPartChar(ch)) {
      return false;
    }
  }

  return true;
}

private static boolean isValidJSIdentifierStartChar(char ch) {
  if (ch == '$' || ch == '_') {
    return true;
  }
  // ASCII letters
  if (ch >= 'A' && ch <= 'Z') {
    return true;
  }
  if (ch >= 'a' && ch <= 'z') {
    return true;
  }
  // Check if Java thinks it's a valid identifier start and not control char
  if (Character.isJavaIdentifierStart(ch) && !isControlCharacter(ch)) {
    return true;
  }
  return false;
}

private static boolean isValidJSIdentifierPartChar(char ch) {
  if (ch == '$' || ch == '_') {
    return true;
  }
  // ASCII letters and digits
  if ((ch >= 'A' && ch <= 'Z') ||
      (ch >= 'a' && ch <= 'z') ||
      (ch >= '0' && ch <= '9')) {
    return true;
  }
  // Check if Java thinks it's a valid identifier part and not control char
  if (Character.isJavaIdentifierPart(ch) && !isControlCharacter(ch)) {
    return true;
  }
  return false;
}

private static boolean isControlCharacter(char ch) {
  // Control characters in 0x00-0x1F and 0x7F-0x9F range are invalid
  return (ch >= 0x00 && ch <= 0x1F) || (ch >= 0x7F && ch <= 0x9F);
}
