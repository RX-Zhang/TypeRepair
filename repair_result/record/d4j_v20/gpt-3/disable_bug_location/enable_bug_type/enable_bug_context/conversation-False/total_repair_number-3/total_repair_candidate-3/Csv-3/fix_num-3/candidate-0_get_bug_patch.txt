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
        // For any other character, return the original escape character
        // followed by the character read. This preserves sequences like \N or \a as is.
        // The caller must handle appending the original escape character if needed.

        // However, since this method returns an int, we can't return two characters here.
        // The fix is to NOT interpret unknown escape sequences by converting them.
        // We return the character as is and let the caller prepend the escape char.

        // So instead of interpreting unknown escape chars, return the char as is.
        return c;
    }
}
