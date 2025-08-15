boolean matchesLetter() {
    if (isEmpty())
        return false;
    char c = input[pos];
    return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= 0x80); // Added check for non-ASCII letters
}
