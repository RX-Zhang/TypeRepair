public void escape(Writer writer, String str) throws IOException {
    int len = str.length();
    for (int i = 0; i < len; i++) {
        char c = str.charAt(i);
        int codepoint;

        // Check if current char is a high surrogate and next char is a low surrogate to form a supplementary codepoint
        if (Character.isHighSurrogate(c) && i + 1 < len && Character.isLowSurrogate(str.charAt(i + 1))) {
            codepoint = Character.toCodePoint(c, str.charAt(i + 1));
            i++; // skip the low surrogate as it is already processed
        } else {
            codepoint = c;
        }

        String entityName = this.entityName(codepoint);
        if (entityName == null) {
            if (codepoint > 0x7F) {
                writer.write("&#");
                writer.write(Integer.toString(codepoint, 10));
                writer.write(';');
            } else {
                writer.write((char)codepoint);
            }
        } else {
            writer.write('&');
            writer.write(entityName);
            writer.write(';');
        }
    }
}
