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
        // For unrecognized escape sequences, return the escape character followed by c
        // i.e. preserve the escape character '\' and the following character as is.
        // Since we have already read the escape char, to preserve it we need to 
        // handle this in the caller or encode it here. 
        // But since this method returns an int (a single char), and is used for decoding,
        // the best we can do is return c unchanged here meaning the escape does not translate.
        // However, the tests show that a \N or \a should be preserved as two characters.
        // So we need to signal that we did not decode this escape, and let the caller prepend the escape char.

        // One approach: return a special value to indicate no decoding, e.g. DISABLED or -2 (not standard here).
        // But given current method signature and usage, we should return c as is, and the caller should handle it.

        return c;
    }
}
