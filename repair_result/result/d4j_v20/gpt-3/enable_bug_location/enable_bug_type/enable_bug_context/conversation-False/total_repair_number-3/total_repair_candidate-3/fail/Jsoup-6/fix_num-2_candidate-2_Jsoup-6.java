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
                // Handle invalid number format
                charval = -1; // Reset charval in case of failure
            }
        } else {
            String name = m.group(1);
            if (full.containsKey(name)) {
                charval = (int) full.get(name); // Ensure charval is an integer
            }
        }

        // Corrected logical check: charval should not be -1 or should be a valid char
        if (charval != -1 && charval <= 0xFFFF) { 
            String c = Character.toString((char) charval);
            m.appendReplacement(accum, c);
        } else {
            m.appendReplacement(accum, m.group(0));
        }
    }
    m.appendTail(accum);
    return accum.toString();
}
