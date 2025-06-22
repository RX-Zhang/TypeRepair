static String unescape(String string) {
    if (!string.contains("&"))
        return string;

    Matcher m = unescapePattern.matcher(string); // &(#(x|X)?([0-9a-fA-F]+)|[a-zA-Z]+);?
    StringBuffer accum = new StringBuffer(string.length()); // pity matcher can't use stringbuilder, avoid syncs

    while (m.find()) {
        int charval = -1;
        String num = m.group(3);
        if (num != null) {
            try {
                int base = m.group(2) != null ? 16 : 10; // 2 is hex indicator
                charval = Integer.valueOf(num, base);
            } catch (NumberFormatException e) {
                // skip if number format is invalid
            }
        } else {
            String name = m.group(1);
            if (name != null && full.containsKey(name)) {
                charval = (int) full.get(name); // assuming full map returns Integer
            }
        }

        if (charval != -1 && charval <= 0xFFFF) { // fixed out of range condition
            String c = Character.toString((char) charval);
            m.appendReplacement(accum, c);
        } else {
            m.appendReplacement(accum, m.group(0)); // fallback to original if charval is out of range
        }
    }
    m.appendTail(accum);
    return accum.toString();
}
