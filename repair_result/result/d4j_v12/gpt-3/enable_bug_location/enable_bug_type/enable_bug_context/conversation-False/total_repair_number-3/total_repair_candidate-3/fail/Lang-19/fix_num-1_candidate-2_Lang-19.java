public int translate(CharSequence input, int index, Writer out) throws IOException {
    int seqEnd = input.length();
    
    // Boundary check for index
    if (index < 0 || index >= seqEnd) {
        return 0; // Invalid index
    }

    // Uses -2 to ensure there is something after the &#
    if (input.charAt(index) == '&' && index < seqEnd - 1 && input.charAt(index + 1) == '#') {
        int start = index + 2;
        boolean isHex = false;

        // Check if there's enough length for start and if the character is 'x' or 'X'
        if (start < seqEnd) {
            char firstChar = input.charAt(start);
            if (firstChar == 'x' || firstChar == 'X') {
                start++;
                isHex = true;

                // Check there's more than just an x after the &#
            }
        } else {
            return 0; // Not enough characters after &#
        }

        int end = start;

        // Ensure we do not go out of bounds while looking for ';'
        while (end < seqEnd && input.charAt(end) != ';') {
            end++;
        }

        // If we reach the end without finding a ';', we consider it an unfinished entity
        if (end == seqEnd) {
            return 0; // Return 0 for unfinished entity
        }

        int entityValue;
        try {
            if (isHex) {
                entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 16);
            } else {
                entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 10);
            }
        } catch (NumberFormatException nfe) {
            System.err.println("FAIL: " + input.subSequence(start, end) + "[" + start + "][" + end + "]");
            return 0;
        }

        if (entityValue > 0xFFFF) {
            char[] chrs = Character.toChars(entityValue);
            out.write(chrs[0]);
            out.write(chrs[1]);
        } else {
            out.write(entityValue);
        }

        return 2 + (end - start) + (isHex ? 1 : 0) + 1;
    }
    return 0;
}
