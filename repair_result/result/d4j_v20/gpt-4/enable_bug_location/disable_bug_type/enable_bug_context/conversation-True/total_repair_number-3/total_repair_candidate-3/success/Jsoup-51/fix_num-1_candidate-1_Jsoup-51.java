boolean matchesLetter() {
    if (isEmpty())
        return false;
    char c = input[pos];
    // Allow letters beyond ASCII, supporting unicode letters
    return Character.isLetter(c);
}
