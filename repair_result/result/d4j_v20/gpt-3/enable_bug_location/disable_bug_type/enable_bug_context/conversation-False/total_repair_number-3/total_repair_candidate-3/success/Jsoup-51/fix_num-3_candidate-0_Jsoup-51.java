boolean matchesLetter() {
    if (isEmpty())
        return false;
    char c = input[pos];
    // Accept Unicode letters, not just ASCII a-zA-Z
    return Character.isLetter(c);
}
