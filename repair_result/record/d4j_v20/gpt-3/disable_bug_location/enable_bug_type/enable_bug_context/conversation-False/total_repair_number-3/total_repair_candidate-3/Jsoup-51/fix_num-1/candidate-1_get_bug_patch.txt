boolean matchesLetter() {
    if (isEmpty())
        return false;
    char c = input[pos];
    // Allow letters beyond just A-Z, a-z, to support non-ASCII letters
    return Character.isLetter(c);
}
