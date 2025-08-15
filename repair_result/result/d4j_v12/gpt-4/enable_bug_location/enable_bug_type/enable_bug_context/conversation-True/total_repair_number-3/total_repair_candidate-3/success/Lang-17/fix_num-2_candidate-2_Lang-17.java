public final void translate(CharSequence input, Writer out) throws IOException {
    if (out == null) {
        throw new IllegalArgumentException("The Writer must not be null");
    }
    if (input == null) {
        return;
    }
    int pos = 0;
    final int len = input.length();
    while (pos < len) {
        int consumed = translate(input, pos, out);
        if (consumed == 0) {
            int codepoint = Character.codePointAt(input, pos);
            char[] chars = Character.toChars(codepoint);
            out.write(chars);
            pos += chars.length;
        } else {
            for (int pt = 0; pt < consumed; pt++) {
                int codepoint = Character.codePointAt(input, pos);
                pos += Character.charCount(codepoint);
            }
        }
    }
}
