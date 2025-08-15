boolean matchesLetter() {
    if (isEmpty())
        return false;
    int c = input[pos];
    return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c > 0x7F);
}
