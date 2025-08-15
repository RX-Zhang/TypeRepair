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

  // Check if the new code starts with a forward slash and the last character is also a forward slash
  // to prevent double slashes in the output.
  if (c == '/' && getLastChar() == '/') {
    // Do not append the new code directly if it starts with a forward slash after another forward slash
    // This will ensure we don't create double slashes in the output.
    append(" ");
  }

  append(newcode);
}
