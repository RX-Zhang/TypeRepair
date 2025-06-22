public void escape(Writer writer, String str) throws IOException {
    int len = str.length();
    for (int i = 0; i < len; i++) {
        int c = str.charAt(i);

        // Check for high surrogate to correctly handle supplementary characters
        if (Character.isHighSurrogate((char)c) && (i + 1) < len) {
            char high = (char)c;
            char low = str.charAt(i + 1);
            if (Character.isLowSurrogate(low)) {
                int codepoint = Character.toCodePoint(high, low);
                String entityName = this.entityName(codepoint);
                if (entityName == null) {
                    writer.write("&#");
                    writer.write(Integer.toString(codepoint, 10));
                    writer.write(';');
                } else {
                    writer.write('&');
                    writer.write(entityName);
                    writer.write(';');
                }
                i++; // skip low surrogate
                continue;
            }
        }

        String entityName = this.entityName(c);
        if (entityName == null) {
            if (c > 0x7F) {
                writer.write("&#");
                writer.write(Integer.toString(c, 10));
                writer.write(';');
            } else {
                writer.write(c);
            }
        } else {
            writer.write('&');
            writer.write(entityName);
            writer.write(';');
        }
    }
}
