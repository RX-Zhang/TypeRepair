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
            // group might be missing, continue to next match
            m.appendReplacement(accum, m.group(0));
            continue;
        }

        if (num != null) {
            try {
                int base = (m.group(2) != null) ? 16 : 10; // 2 is hex indicator
                charval = Integer.valueOf(num, base);
            } catch (NumberFormatException | IllegalArgumentException e) {
                // skip invalid number format
                m.appendReplacement(accum, m.group(0));
                continue;
            }
        } else {
            String name = null;
            try {
                name = m.group(1);
            } catch (IllegalStateException | IndexOutOfBoundsException e) {
                m.appendReplacement(accum, m.group(0));
                continue;
            }
            if (name != null && full.containsKey(name)) {
                Object val = full.get(name);
                if (val instanceof Integer) {
                    charval = (Integer) val;
                }
            }
        }

        if (charval != -1 && charval <= 0xFFFF) { // valid range
            String c = Character.toString((char) charval);
            m.appendReplacement(accum, Matcher.quoteReplacement(c));
        } else {
            m.appendReplacement(accum, Matcher.quoteReplacement(m.group(0)));
        }
    }
    m.appendTail(accum);
    return accum.toString();
}
