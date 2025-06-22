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
    case 'N':
        // preserve '\N' sequence as literal backslash + N for MySQL NULL representation
        return '\\';
    case '\\':
        // preserve escape char if it is escaped
        return '\\';
    default:
        // For any other escaped char, return it as is, including the escape char
        // by returning escape char and the next char in sequence
        // this requires a change in the calling code, but here just return c
        return c;
    }
}
