boolean matchesLetter() {
    if (isEmpty())
        return false;
    int c = input[pos];
    return Character.isLetter(c);
}
