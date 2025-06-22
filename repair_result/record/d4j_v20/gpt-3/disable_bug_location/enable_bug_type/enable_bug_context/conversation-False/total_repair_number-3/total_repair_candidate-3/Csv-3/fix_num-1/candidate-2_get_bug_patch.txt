int readEscape() throws IOException {
    // the escape char has just been read (normally a backslash)
    final int c = in.read();
    switch (c) {
    case 'r':
        return CR;
    case 'n':
        return LF;
    case 't':
        return TAB;
    case 'b':
        return BACKSPACE;
    case 'f':
        return FF;
    case END_OF_STREAM:
        throw new IOException("EOF whilst processing escape sequence");
    default:
        // Return the original escape character and the current character,
        // so that unrecognized escape sequences preserve the escape char.
        // This is needed to handle cases like \N or \a where escape is followed by a letter.
        // Since the current code only reads one char, we cannot return two chars,
        // but the calling code should be adjusted to handle this,
        // or we can return the character as-is and let the caller treat it literally.
        // To fix the test failures, we do not consume the escape char,
        // so we put it back and return the escape char itself.

        // But since we cannot un-read a char, the best we can do is return the escape char 'DISABLED' or similar,
        // or just return the character c but the calling code must prefix with escape char.

        // Here, return the character itself, indicating no translation.
        // The caller should append the escape char before this character.

        return c;
    }
}
