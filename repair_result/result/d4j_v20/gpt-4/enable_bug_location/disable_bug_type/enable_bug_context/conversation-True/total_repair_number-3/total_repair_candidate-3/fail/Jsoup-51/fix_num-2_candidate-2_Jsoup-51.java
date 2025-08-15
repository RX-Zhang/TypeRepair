boolean matchesLetter() {
    if (isEmpty())
        return false;
    char c = input[pos];
    // Accept any character that can start a tag name, including non-ASCII chars.
    // So instead of checking just ASCII letters, delegate to consumeTagName logic.
    // But here just check that the character is not a control character or space.
    return c > 0x20 && c != '/' && c != '>' && c != TokeniserState.nullChar;
}
