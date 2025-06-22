public int translate(CharSequence input, int index, Writer out) throws IOException {
    int seqEnd = input.length();
    // Uses -2 to ensure there is something after the &#
    if(input.charAt(index) == '&' && index < seqEnd - 1 && input.charAt(index + 1) == '#') {
        int start = index + 2;
        boolean isHex = false;

        // Check that start is within bounds before accessing
        if (start >= seqEnd) {
            return 0;
        }

        char firstChar = input.charAt(start);
        if(firstChar == 'x' || firstChar == 'X') {
            start++;
            isHex = true;

            // Check there's more than just an x after the &#
            if (start >= seqEnd) {
                return 0;
            }
        }

        int end = start;
        // Note that this supports character codes without a ; on the end
        while(end < seqEnd && input.charAt(end) != ';') {
            end++;
        }

        // If we've reached the end and no ';' found, treat as unfinished entity and handle gracefully
        if (end == seqEnd) {
            // We do not throw exception, just return 0 to not consume any chars
            // or optionally, parse to the end as numeric entity without semicolon
            // Here we choose to parse till end (unfinished entity)
            // To avoid StringIndexOutOfBounds we set end = seqEnd
        }

        // Ensure start < end to avoid parsing empty string
        if (start >= end) {
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

        // Calculate consumed chars:
        // 2 for '&#', plus length of numeric part (end - start),
        // plus 1 if hex (for 'x' or 'X'), plus 1 if semicolon present
        int consumed = 2 + (end - start) + (isHex ? 1 : 0);
        if (end < seqEnd && input.charAt(end) == ';') {
            consumed += 1;
        }

        return consumed;
    }
    return 0;
}
