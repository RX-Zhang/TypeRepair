public StrBuilder appendFixedWidthPadRight(Object obj, int width, char padChar) {
    if (width > 0) {
        String str = (obj == null ? getNullText() : obj.toString());
        int strLen = str.length();
        ensureCapacity(size + width);
        if (strLen >= width) {
            str.getChars(0, width, buffer, size);
        } else {
            int padLen = width - strLen;
            str.getChars(0, strLen, buffer, size);
            for (int i = 0; i < padLen; i++) {
                buffer[size + strLen + i] = padChar;
            }
        }
        size += width;
    }
    return this;
}
