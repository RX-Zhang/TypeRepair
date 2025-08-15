public int translate(CharSequence input, int index, Writer out) throws IOException {
    // Protect from ArrayIndexOutOfBounds
    if (index + 2 >= input.length()) {
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
            if (start >= input.length()) {
                return 0;
            }
        }

        int end = start;
        while (end < input.length() && input.charAt(end) != ';') {
            end++;
        }
        if (end == input.length()) {
            return 0; // no terminating ';'
        }

        int entityValue;
        try {
            entityValue = Integer.parseInt(input.subSequence(start, end).toString(), isHex ? 16 : 10);
        } catch (NumberFormatException nfe) {
            return 0;
        }

        if (entityValue > 0xFFFF) {
            // Supplementary character, write surrogate pair
            char[] surrogates = Character.toChars(entityValue);
            out.write(surrogates[0]);
            out.write(surrogates[1]);
        } else {
            out.write(entityValue);
        }

        return (end - index) + 1;
    }
    return 0;
}
