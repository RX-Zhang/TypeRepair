public static boolean isJSIdentifier(String s) {
  int length = s.length();

  if (length == 0) {
    return false;
  }

  char firstChar = s.charAt(0);
  // Use more restrictive ASCII check for first character
  if (!((firstChar >= 'A' && firstChar <= 'Z') || 
        (firstChar >= 'a' && firstChar <= 'z') || 
        firstChar == '_' || firstChar == '$')) {
    return false;
  }

  for (int i = 1; i < length; i++) {
    char c = s.charAt(i);
    // More restrictive ASCII check for identifier parts
    if (!((c >= 'A' && c <= 'Z') || 
          (c >= 'a' && c <= 'z') || 
          (c >= '0' && c <= '9') || 
          c == '_' || c == '$')) {
      return false;
    }
  }

  return true;
}
