int readEscape() throws IOException {
    // the escape char has just been read (normally a backslash or other configured escape char)
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
        // If the character after escape is not a recognized escape sequence character,
        // then return the escape character itself plus that character, preserving the literal.
        // To do that, push back the char 'c' and return the literal escape char so that
        // it is included verbatim.
        in.unread(c); // push back the char since it's not a special escape
        // Return the escape char to represent the literal escape sequence (e.g., \N or \a)
        return escape;
    }
}
