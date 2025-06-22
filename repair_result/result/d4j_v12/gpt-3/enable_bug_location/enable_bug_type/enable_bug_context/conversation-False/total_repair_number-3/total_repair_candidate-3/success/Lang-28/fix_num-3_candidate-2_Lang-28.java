public int translate(CharSequence input, int index, Writer out) throws IOException {
    // Protect from ArrayIndexOutOfBoundsException
    if (index + 1 >= input.length()) {
        return 0;
    }
    if (input.charAt(index) == '&' && input.charAt(index + 1) == '#') {
        int start = index + 2;
        boolean isHex = false;

        if (start >= input.length()) {
            return 0;
        }
        char firstChar = input.charAt(start);
        if (firstChar == 'x' || firstChar == 'X') {
            start++;
            isHex = true;
        }

        int end = start;
        while (end < input.length() && input.charAt(end) != ';') {
            end++;
        }
        if (end == input.length()) {
            return 0; // no closing semicolon found
        }

        int entityValue;
        try {
            String numericString = input.subSequence(start, end).toString();
            if (isHex) {
                entityValue = Integer.parseInt(numericString, 16);
            } else {
                entityValue = Integer.parseInt(numericString, 10);
            }
        } catch (NumberFormatException nfe) {
            return 0;
        }

        if (Character.isBmpCodePoint(entityValue)) {
            // single char
            out.write(entityValue);
        } else if (Character.isValidCodePoint(entityValue)) {
            // supplementary character - write surrogate pair
            out.write(Character.highSurrogate(entityValue));
            out.write(Character.lowSurrogate(entityValue));
        } else {
            // invalid code point; treat as no translation
            return 0;
        }

        return 2 + (end - start) + (isHex ? 1 : 0) + 1;
    }
    return 0;
}
