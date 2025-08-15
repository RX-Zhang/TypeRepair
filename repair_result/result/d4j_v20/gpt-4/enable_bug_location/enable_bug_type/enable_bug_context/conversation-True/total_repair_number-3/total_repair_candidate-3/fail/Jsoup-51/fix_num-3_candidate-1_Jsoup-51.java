boolean matchesLetter() {
    if (isEmpty())
        return false;
    char c = current();
    return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
}
