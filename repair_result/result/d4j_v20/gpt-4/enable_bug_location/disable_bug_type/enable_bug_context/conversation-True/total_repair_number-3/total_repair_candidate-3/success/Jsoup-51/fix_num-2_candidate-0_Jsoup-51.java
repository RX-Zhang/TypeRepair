boolean matchesLetter() {
    if (isEmpty())
        return false;
    char c = input[pos];
    // Accept non-ASCII letters as valid tag name characters, allowing for Unicode tag names
    return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c > 0x7F);
}
