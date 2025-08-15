public int translate(CharSequence input, int index, Writer out) throws IOException {
    int seqEnd = input.length();
    // Uses -2 to ensure there is something after the &#
    if(input.charAt(index) == '&' && index < seqEnd - 1 && input.charAt(index + 1) == '#') {
        int start = index + 2;
        boolean isHex = false;

        // Check bounds before accessing charAt(start)
        if (start >= seqEnd) {
            return 0;
        }

        char firstChar = input.charAt(start);
        if(firstChar == 'x' || firstChar == 'X') {
            start++;
            isHex = true;

            // Check that after 'x' there is at least one more character
            if (start >= seqEnd) {
                return 0;
            }
        }

        int end = start;
        // Note that this supports character codes without a ; on the end
        // We need to be careful about index out of bounds in the while loop
        while(end < seqEnd && input.charAt(end) != ';') {
            end++;
        }

        // If no semicolon found and we've reached the end, we handle as unfinished entity
        if (end == seqEnd) {
            // This allows unfinished entity (without semicolon)
            // but do not proceed if there's no digit to parse
            if (start == seqEnd) {
                return 0;
            }
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

        // The length of the entity:
        // '&' + '#' + optional 'x' + digits + optional ';'
        // We subtract index from end for the digits count, add 2 + isHex + optional 1 for ';' if present
        int length = 2 + (end - start) + (isHex ? 1 : 0);
        if (end < seqEnd && input.charAt(end) == ';') {
            length += 1;
        }
        return length;
    }
    return 0;
}
