void add(String newcode) {
  maybeEndStatement();

  if (newcode.length() == 0) {
    return;
  }

  char c = newcode.charAt(0);
  if ((isWordChar(c) || c == '\\') && isWordChar(getLastChar())) {
    // need space to separate. This is not pretty printing.
    // For example: "return foo;"
    append(" ");
  }

  // Check if the last character is a forward slash and the current character is also a forward slash
  // to avoid printing multiple forward slashes in succession.
  if (getLastChar() == '/' && c == '/') {
    // If the last character is a forward slash, we should not append another one directly.
    append(" ");
  }

  append(newcode);
}
