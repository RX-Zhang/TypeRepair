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
        // Do not consume the escape, return the escaped char including those 
        // like \N (MySQL null) or any other escaped char, so keep the escape char
        // along with the next character
        // Return the character as is but indicate it was escaped by returning both chars
        // since this method returns int, we need to handle this at a higher level,
        // but here just return c and let the caller handle possible escaped sequences.
        // So return c as read.
        return c;
    }
}
