public int translate(CharSequence input, int index, Writer out) throws IOException {
    int seqEnd = input.length();
    // Uses -2 to ensure there is something after the &#
    if(input.charAt(index) == '&' && index < seqEnd - 1 && input.charAt(index + 1) == '#') {
        int start = index + 2;
        boolean isHex = false;

        if(start >= seqEnd) {
            // No characters after &#, return 0 to indicate no translation
            return 0;
        }

        char firstChar = input.charAt(start);
        if(firstChar == 'x' || firstChar == 'X') {
            start++;
            isHex = true;

            if(start >= seqEnd) {
                // No characters after &#x or &#X, return 0 to indicate no translation
                return 0;
            }
        }

        int end = start;
        // Note that this supports character codes without a ; on the end
        // So the loop should not go out of bounds, check bounds first
        while(end < seqEnd && input.charAt(end) != ';') {
            end++;
        }

        // if no semicolon found, end == seqEnd, treat as unfinished entity so we do not translate
        if(end == seqEnd) {
            return 0;
        }

        int entityValue;
        try {
            if(isHex) {
                entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 16);
            } else {
                entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 10);
            }
        } catch(NumberFormatException nfe) {
            System.err.println("FAIL: " + input.subSequence(start, end) + "[" + start + "][" + end + "]");
            return 0;
        }

        if(entityValue > 0xFFFF) {
            char[] chrs = Character.toChars(entityValue);
            out.write(chrs[0]);
            out.write(chrs[1]);
        } else {
            out.write(entityValue);
        }

        return 2 + (end - (index + 2)) + (isHex ? 1 : 0) + 1;
    }

    return 0;
}
