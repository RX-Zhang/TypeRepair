static String unescape(String string) {
    if (!string.contains("&"))
        return string;

    Matcher m = unescapePattern.matcher(string); // &(#(x|X)?([0-9a-fA-F]+)|[a-zA-Z]+);?
    StringBuffer accum = new StringBuffer(string.length()); // pity matcher can't use stringbuilder, avoid syncs

    while (m.find()) {
        int charval = -1;
        String num = m.group(3);
        try {
            if (num != null) {
                int base = m.group(2) != null ? 16 : 10; // 2 is hex indicator
                charval = Integer.parseInt(num, base);
            } else {
                String name = m.group(1);
                if (name != null && full.containsKey(name))
                    charval = (Integer) full.get(name);
            }
        } catch (NumberFormatException | IllegalArgumentException e) {
            // Skip invalid numeric or unknown group references safely
            charval = -1;
        }

        // Validate charval range: valid Unicode codepoint and non-negative
        if (charval >= 0 && charval <= 0xFFFF) {
            String c = Character.toString((char) charval);
            // Use quoteReplacement to avoid issues with special characters in replacement string
            m.appendReplacement(accum, Matcher.quoteReplacement(c));
        } else {
            m.appendReplacement(accum, Matcher.quoteReplacement(m.group(0)));
        }
    }
    m.appendTail(accum);
    return accum.toString();
}
