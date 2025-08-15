static boolean isSimpleNumber(String s) {
  if (s.isEmpty()) {
    return false;
  }
  int len = s.length();
  for (int index = 0; index < len; index++) {
    char c = s.charAt(index);
    // allow digit or dot or hex indicator characters for simple number check
    if (!((c >= '0' && c <= '9') || c == '.' || c == 'x' || c == 'X')) {
      return false;
    }
  }
  return true;
}
