boolean matchesLetter() {
    if (isEmpty())
        return false;
    char c = input[pos];
    // Allow non-ASCII letters, so accept anything other than control or special chars in tag name
    // Using the same logic as consumeTagName: disallow tab, newline, carriage return, form feed, space, '/', '>', nullChar
    return c != '\t' && c != '\n' && c != '\r' && c != '\f' && c != ' ' && c != '/' && c != '>' && c != 0;
}
