public void escape(Writer writer, String str) throws IOException {
    int len = str.length();
    for (int i = 0; i < len;) {
        int codepoint = str.codePointAt(i);
        i += Character.charCount(codepoint);
        String entityName = this.entityName(codepoint);
        if (entityName == null) {
            if (codepoint > 0x7F) {
                writer.write("&#");
                writer.write(Integer.toString(codepoint, 10));
                writer.write(';');
            } else {
                writer.write(codepoint);
            }
        } else {
            writer.write('&');
            writer.write(entityName);
            writer.write(';');
        }
    }
}
