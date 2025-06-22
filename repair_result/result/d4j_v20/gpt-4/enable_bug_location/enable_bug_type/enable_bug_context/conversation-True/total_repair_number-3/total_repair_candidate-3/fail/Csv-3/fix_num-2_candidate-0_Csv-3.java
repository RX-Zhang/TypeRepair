int readEscape() throws IOException {
    // the escape char has just been read (normally a backslash or custom escape char)
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
        // Return the escaped character as-is, including meta-characters like escape or delimiter 
        // as they should be preserved and not removed (e.g., escape + delimiter = literal delimiter)
        return c;
    }
}
