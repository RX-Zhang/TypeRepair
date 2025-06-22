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
        // Return the character prefixed by escape char without consuming it
        // i.e. return escape char followed by c as two separate chars merged into one int
        // Since readEscape is expected to return a single int, we must handle escaped characters literally
        // Here, we return c as is, as the escape char needs to be preserved by the caller.
        return c;
    }
}
