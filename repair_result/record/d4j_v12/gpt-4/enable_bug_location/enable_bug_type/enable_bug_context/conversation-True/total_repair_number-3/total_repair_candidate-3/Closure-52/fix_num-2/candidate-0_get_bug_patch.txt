static boolean isSimpleNumber(String s) {
  int len = s.length();
  boolean hasDot = false;
  for (int index = 0; index < len; index++) {
    char c = s.charAt(index);
    if (c == '.') {
      if (hasDot) {
        return false;
      }
      hasDot = true;
    } else if (c < '0' || c > '9') {
      return false;
    }
  }
  return len > 0;
}
