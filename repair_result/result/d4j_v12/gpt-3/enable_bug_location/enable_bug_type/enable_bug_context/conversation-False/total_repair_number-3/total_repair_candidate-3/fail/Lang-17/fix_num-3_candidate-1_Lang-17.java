public final void translate(CharSequence input, Writer out) throws IOException {
    if (out == null) {
        throw new IllegalArgumentException("The Writer must not be null");
    }
    if (input == null) {
        return;
    }
    int pos = 0;
    int len = Character.codePointCount(input, 0, input.length());
    while (pos < len) {
        int consumed = translate(input, pos, out);
        if (consumed == 0) {
            char[] c = Character.toChars(Character.codePointAt(input, pos));
            out.write(c);
            pos += Character.charCount(Character.codePointAt(input, pos)); // Move position forward by the character length
        } else {
            // contract with translators is that they have to understand codepoints 
            // and they just took care of a surrogate pair
            pos += consumed; // Correctly move position forward by the consumed length
        }
    }
}
