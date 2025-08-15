public int translate(CharSequence input, int index, Writer out) throws IOException {
    int seqEnd = input.length();
    // Uses -2 to ensure there is something after the &#
    if(input.charAt(index) == '&' && index < seqEnd - 1 && input.charAt(index + 1) == '#') {
        int start = index + 2;
        boolean isHex = false;

        // Check that start is within the bounds before accessing input.charAt(start)
        if(start >= seqEnd) {
            return 0;
        }
        
        char firstChar = input.charAt(start);
        if(firstChar == 'x' || firstChar == 'X') {
            start++;
            isHex = true;

            // Check there's more than just an x after the &#
            if(start >= seqEnd) {
                return 0;
            }
        }

        int end = start;
        // Note that this supports character codes without a ; on the end
        // So we need to loop until the end of string or until we find a ';'
        while(end < seqEnd && input.charAt(end) != ';') {
            end++;
        }

        // If no ';' found and end reached seqEnd, handle accordingly (allow unfinished entities)
        if(end == seqEnd && (end == start)) {
            // Nothing to parse, no digits or hex value following
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

        // Calculate length of processed input:
        // 2 for '&#'
        // (end - start) for number of characters in entity number
        // (isHex ? 1 : 0) for 'x' or 'X'
        // +1 for the optional ';' if present (only if input.charAt(end) == ';')
        int consumed = 2 + (end - start) + (isHex ? 1 : 0);
        if(end < seqEnd && input.charAt(end) == ';') {
            consumed += 1;
        }
        return consumed;
    }
    return 0;
}
