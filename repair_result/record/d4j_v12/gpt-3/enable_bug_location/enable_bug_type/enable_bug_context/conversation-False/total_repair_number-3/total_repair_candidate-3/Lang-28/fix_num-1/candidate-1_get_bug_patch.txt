public int translate(CharSequence input, int index, Writer out) throws IOException {
    // Protect from ArrayIndexOutOfBounds
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
            String numStr = input.subSequence(start, end).toString();
            if (isHex) {
                entityValue = Integer.parseInt(numStr, 16);
            } else {
                entityValue = Integer.parseInt(numStr, 10);
            }
        } catch (NumberFormatException nfe) {
            return 0;
        }

        if (entityValue > 0xFFFF) {
            // supplementary character, output surrogate pair
            char[] chars = Character.toChars(entityValue);
            out.write(chars[0]);
            out.write(chars[1]);
        } else {
            // BMP character
            out.write(entityValue);
        }

        return 2 + (end - (index + 2)) + (isHex ? 1 : 0) + 1;
    }
    return 0;
}
