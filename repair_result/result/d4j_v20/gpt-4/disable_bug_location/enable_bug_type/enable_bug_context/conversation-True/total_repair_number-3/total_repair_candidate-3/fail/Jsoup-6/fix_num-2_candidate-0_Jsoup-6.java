static String unescape(String string) {
    if (!string.contains("&"))
        return string;

    Matcher m = unescapePattern.matcher(string); // &(#(x|X)?([0-9a-fA-F]+)|[a-zA-Z]+);?
    StringBuffer accum = new StringBuffer(string.length()); // pity matcher can't use stringbuilder, avoid syncs
    // todo: replace m.appendReplacement with own impl, so StringBuilder and quoteReplacement not required

    while (m.find()) {
        int charval = -1;
        String num = null;
        try {
            num = m.group(3);
        } catch (IllegalStateException | IndexOutOfBoundsException e) {
            // group might not exist, treat as null
            num = null;
        }
        if (num != null) {
            try {
                int base = (m.group(2) != null) ? 16 : 10; // 2 is hex indicator
                charval = Integer.valueOf(num, base);
            } catch (NumberFormatException e) {
            } // skip
        } else {
            String name = null;
            try {
                name = m.group(1);
            } catch (IllegalStateException | IndexOutOfBoundsException e) {
                name = null;
            }
            if (name != null && full.containsKey(name)) {
                Object val = full.get(name);
                if (val instanceof Integer) {
                    charval = (Integer) val;
                } else if (val instanceof Number) {
                    charval = ((Number) val).intValue();
                }
            }
        }

        if (charval != -1 && charval <= 0xFFFF) { // valid BMP char only
            String c = Character.toString((char) charval);
            // Escape special characters in replacement string
            String safe = Matcher.quoteReplacement(c);
            m.appendReplacement(accum, safe);
        } else {
            String original = null;
            try {
                original = m.group(0);
            } catch (IllegalStateException | IndexOutOfBoundsException e) {
                original = "";
            }
            m.appendReplacement(accum, Matcher.quoteReplacement(original));
        }
    }
    m.appendTail(accum);
    return accum.toString();
}
