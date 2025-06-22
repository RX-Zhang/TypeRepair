void add(String newcode) {
  maybeEndStatement();

  if (newcode.length() == 0) {
    return;
  }

  char c = newcode.charAt(0);
  // Correcting the logical error in the condition to ensure proper spacing
  if ((isWordChar(c) || c == '\\') && !isWordChar(getLastChar())) {
    // Need space to separate. This is not pretty printing.
    // For example: "return foo;"
    append(" ");
  }

  append(newcode);
}
