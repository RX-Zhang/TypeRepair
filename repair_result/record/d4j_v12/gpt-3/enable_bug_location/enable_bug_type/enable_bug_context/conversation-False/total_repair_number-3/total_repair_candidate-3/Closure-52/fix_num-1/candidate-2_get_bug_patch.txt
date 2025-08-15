static boolean isSimpleNumber(String s) {
  int len = s.length();
  // Check for empty strings
  if (len == 0) {
    return false;
  }
  
  // Check for valid numeric characters
  for (int index = 0; index < len; index++) {
    char c = s.charAt(index);
    if (c < '0' || c > '9') {
      // Allow for a leading zero in case of octal representation
      if (index == 0 && c == '0' && len > 1) {
        return false; // More than one digit and starts with '0' is not a valid simple number
      }
      return false;
    }
  }
  return true; // Valid simple number
}
