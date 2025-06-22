public StrBuilder appendFixedWidthPadRight(Object obj, int width, char padChar) {
    if (width > 0) {
        String str = (obj == null ? getNullText() : obj.toString());
        int strLen = str.length();
        if (strLen >= width) {
            ensureCapacity(size + width);
            str.getChars(0, width, buffer, size);
            size += width;
        } else {
            ensureCapacity(size + width);
            str.getChars(0, strLen, buffer, size);
            int padLen = width - strLen;
            for (int i = 0; i < padLen; i++) {
                buffer[size + strLen + i] = padChar;
            }
            size += width;
        }
    }
    return this;
}
