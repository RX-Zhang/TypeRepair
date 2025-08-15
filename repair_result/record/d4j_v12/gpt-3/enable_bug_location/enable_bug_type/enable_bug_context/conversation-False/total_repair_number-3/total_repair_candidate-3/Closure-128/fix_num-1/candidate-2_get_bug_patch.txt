static boolean isSimpleNumber(String s) {
  int len = s.length();
  if (len == 0 || (len > 1 && s.charAt(0) == '0')) {
    return false; // Additional check to ensure that strings like "01" are not considered valid
  }
  for (int index = 0; index < len; index++) {
    char c = s.charAt(index);
    if (c < '0' || c > '9') {
      return false;
    }
  }
  return true; // If all characters are digits and first character is not '0' (except for "0")
}
