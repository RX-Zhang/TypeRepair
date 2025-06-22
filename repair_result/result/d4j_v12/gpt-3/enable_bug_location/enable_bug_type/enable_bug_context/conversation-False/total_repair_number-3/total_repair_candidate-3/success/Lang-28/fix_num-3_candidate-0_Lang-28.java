public int translate(CharSequence input, int index, Writer out) throws IOException {
    // Protect from ArrayIndexOutOfBounds
    if (index + 3 >= input.length()) {
        return 0;
    }

    if (input.charAt(index) == '&' && input.charAt(index + 1) == '#') {
        int start = index + 2;
        boolean isHex = false;

        char firstChar = input.charAt(start);
        if (firstChar == 'x' || firstChar == 'X') {
            start++;
            isHex = true;
            if (start >= input.length()) {
                return 0;
            }
        }

        int end = start;
        while (end < input.length() && input.charAt(end) != ';') {
            end++;
        }

        if (end == input.length()) {
            return 0; // No terminating ';'
        }

        int entityValue;
        try {
            if (isHex) {
                entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 16);
            } else {
                entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 10);
            }
        } catch (NumberFormatException nfe) {
            return 0;
        }

        // Correctly handle supplementary characters (code points above U+FFFF)
        if (Character.isValidCodePoint(entityValue)) {
            if (entityValue > 0xFFFF) {
                // Characters outside BMP -> write surrogate pair
                out.write(Character.toChars(entityValue));
            } else {
                out.write(entityValue);
            }
            return end - index + 1; // Total length of the entity including '&#', optional 'x', digits, and ';'
        }

        return 0;
    }
    return 0;
}
