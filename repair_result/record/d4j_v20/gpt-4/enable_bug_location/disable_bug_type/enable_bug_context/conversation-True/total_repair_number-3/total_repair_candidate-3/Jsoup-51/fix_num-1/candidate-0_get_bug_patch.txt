boolean matchesLetter() {
    if (isEmpty())
        return false;
    char c = input[pos];
    // Support for Unicode letters, including non-ASCII
    return Character.isLetter(c);
}
