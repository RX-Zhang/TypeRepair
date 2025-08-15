boolean matchesLetter() {
    if (isEmpty())
        return false;
    char c = input[pos];
    return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= 128); // Added condition for non-ASCII characters
}
