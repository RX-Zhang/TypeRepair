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
    case FF: // TODO is this correct?
    case TAB: // TODO is this correct? Do tabs need to be escaped?
    case BACKSPACE: // TODO is this correct?
        return c;
    case END_OF_STREAM:
        throw new IOException("EOF whilst processing escape sequence");
    default:
        // Now check for meta-characters and ensure we handle backslash escapes correctly
        if (c == '\\') { // Check for backslash escape character
            final int nextChar = in.read(); // Read next char after backslash
            if (nextChar == 'N') { // Handle MySQL null value escape
                return '\0'; // Return null character or appropriate value for null
            } else if (nextChar == 'a') { // Handle escaped character
                return 'a'; // Return the character a
            } else {
                return nextChar; // Return the next character as is
            }
        }
        return c; // Return the unrecognized character
    }
}
