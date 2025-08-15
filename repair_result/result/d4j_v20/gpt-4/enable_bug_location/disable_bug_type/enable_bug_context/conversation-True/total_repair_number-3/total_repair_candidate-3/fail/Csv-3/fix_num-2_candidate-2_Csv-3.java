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
    case '0':
        // handle null character (as seen for the MySQL \N escape case)
        return DISABLED;
    case END_OF_STREAM:
        throw new IOException("EOF whilst processing escape sequence");
    case '\\':
    case '/':
        // support for escaping escape character itself or other escape chars like '/'
        return c;
    default:
        // if next char is not a recognized escape sequence, 
        // preserve the escape char and the following char.
        // Return the character as is.
        return c;
    }
}
