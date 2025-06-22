public int translate(CharSequence input, int index, Writer out) throws IOException {
    // TODO: Protect from ArrayIndexOutOfBounds
    if(input.charAt(index) == '&' && input.charAt(index + 1) == '#') {
        int start = index + 2;
        boolean isHex = false;

        char firstChar = input.charAt(start);
        if(firstChar == 'x' || firstChar == 'X') {
            start++;
            isHex = true;
        }

        int end = start;
        while(input.charAt(end) != ';') {
            end++;
        }

        int entityValue;
        try {
            if(isHex) {
                entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 16);
            } else {
                entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 10);
            }
        } catch(NumberFormatException nfe) {
            return 0;
        }

        if (Character.isBmpCodePoint(entityValue)) {
            out.write(entityValue);
        } else if (Character.isValidCodePoint(entityValue)) {
            char[] chars = Character.toChars(entityValue);
            out.write(chars[0]);
            out.write(chars[1]);
        } else {
            // Invalid code point; don't write anything
            return 0;
        }

        return 2 + (end - index);
    }
    return 0;
}
