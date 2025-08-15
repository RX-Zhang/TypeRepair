public int translate(CharSequence input, int index, Writer out) throws IOException {
    int seqEnd = input.length();
    // Uses -2 to ensure there is something after the &#
    if(input.charAt(index) == '&' && index < seqEnd - 1 && input.charAt(index + 1) == '#') {
        int start = index + 2;
        boolean isHex = false;

        if (start >= seqEnd) {
            // no chars after '&#'
            return 0;
        }

        char firstChar = input.charAt(start);
        if(firstChar == 'x' || firstChar == 'X') {
            start++;
            isHex = true;

            if (start >= seqEnd) {
                // no chars after '&#x' or '&#X'
                return 0;
            }
        }

        int end = start;
        // Note that this supports character codes without a ; on the end
        while(end < seqEnd && input.charAt(end) != ';') 
        {
            end++;
        }

        // if we reached the end without finding ';', end should stay at seqEnd (unfinished entity)
        // but we want to allow unfinished entities per the test, so no exception here

        if (end == seqEnd) {
            // no semicolon found - unfinished entity, parse until end
        }

        if(end <= start) {
            // no digits after '&#' or '&#x'
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
            System.err.println("FAIL: " + input.subSequence(start, end) + "[" + start +"]["+ end +"]");
            return 0;
        }

        if(entityValue > 0xFFFF) {
            char[] chrs = Character.toChars(entityValue);
            out.write(chrs[0]);
            out.write(chrs[1]);
        } else {
            out.write(entityValue);
        }

        // Calculate consumed length: 
        // 2 for '&#', plus length of number (end - start), plus 1 if hex (for x or X), plus 1 if semicolon present
        int consumed = 2 + (end - start) + (isHex ? 1 : 0);
        if (end < seqEnd && input.charAt(end) == ';') {
            consumed++;
        }
        return consumed; 
    }
    return 0;
}
