boolean matchesLetter() {
    if (isEmpty())
        return false;
    char c = input[pos];
    // Accept any character that is a Unicode letter (not just ASCII letters)
    return Character.isLetter(c);
}
