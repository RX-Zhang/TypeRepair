private char getMappingCode(final String str, final int index) {
    // map() throws IllegalArgumentException
    final char mappedChar = this.map(str.charAt(index));
    // HW rule check
    if (index > 1 && mappedChar != '0') {
        final char hwChar = str.charAt(index - 1);
        if (hwChar == 'H' || hwChar == 'W') {
            final char preHWChar = str.charAt(index - 2);
            final char firstCode = this.map(preHWChar);
            if (firstCode == mappedChar || preHWChar == 'H' || preHWChar == 'W') {
                return '0'; // Changed from 0 to '0' to return a char
            }
        }
    }
    return mappedChar;
}
