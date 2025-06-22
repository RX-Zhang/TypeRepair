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
    case CR:
    case LF:
    case FF:
    case TAB:
    case BACKSPACE:
        return c;
    case END_OF_STREAM:
        throw new IOException("EOF whilst processing escape sequence");
    default:
        // If the next character is the escape char itself,
        // return the escaped character (the escape char),
        // preserving the escape in the output.
        if (c == escape) {
            return escape;
        }
        // If the next character is the quote char,
        // return the quote char, preserving the escape.
        if (c == quoteChar) {
            return quoteChar;
        }
        // If the next character is the delimiter,
        // return the delimiter, preserving the escape.
        if (c == delimiter) {
            return delimiter;
        }
        // If none of the above, treat escape as a literal character
        // and push back the read character.
        // But since pushing back is not indicated, we'll just return c.
        return c;
    }
}
