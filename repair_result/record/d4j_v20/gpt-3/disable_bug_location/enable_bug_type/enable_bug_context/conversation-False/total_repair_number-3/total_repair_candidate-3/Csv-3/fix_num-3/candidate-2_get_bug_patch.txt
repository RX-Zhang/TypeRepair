int readEscape() throws IOException {
    // the escape char has just been read (normally a backslash or other escape char)
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
        // For any character not recognized as a standard escape sequence,
        // return the char itself, preserving escaped meta-characters like 'N' for \N (MySQL null)
        // or any other char without interpreting it.
        return c;
    }
}
