int readEscape() throws IOException {
    // the escape char has just been read (normally a backslash)
    final int c = in.read();
    if (c == END_OF_STREAM) {
        throw new IOException("EOF whilst processing escape sequence");
    }
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
        default:
            // If the char is not a known escape sequence, 
            // if it matches the escape or quoteChar then return it as is
            if (c == escape || c == quoteChar) {
                return c;
            }
            // For other characters, return them as is (preserving the escape)
            return c;
    }
}
