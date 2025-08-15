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
    case 'N':
        // Preserve escaped 'N' (e.g., MySQL \N for null) by returning the literal characters
        // so that the escape sequence does not get converted, but the \ and N are preserved.
        // We do this by first pushing back the read char 'N' so that the caller can handle it as normal chars.
        in.unread(c);
        return '\\'; // Return the escape char to be processed as literal '\'
    case END_OF_STREAM:
        throw new IOException("EOF whilst processing escape sequence");
    default:
        // For any other character, do not consume the escape,
        // instead return the escape char then the character, so "\a" becomes "\a" literally.
        in.unread(c);
        return '\\';
    }
}
