private char getMappingCode(final String str, final int index) {
    final char mappedChar = this.map(str.charAt(index));
    if (index > 1 && mappedChar != '0') {
        final char hwChar = str.charAt(index - 1);
        if (hwChar == 'H' || hwChar == 'W') {
            final char preHWChar = str.charAt(index - 2);
            final char firstCode = this.map(preHWChar);
            if (firstCode == mappedChar || mappedChar == '0') {
                return '0';
            }
        }
    }
    return mappedChar;
}
