public StrBuilder appendFixedWidthPadRight(Object obj, int width, char padChar) {
    if (width > 0) {
        String str = (obj == null ? getNullText() : obj.toString());
        int strLen = (str == null ? 0 : str.length());
        if (strLen >= width) {
            ensureCapacity(size + width);
            str.getChars(0, width, buffer, size);
            size += width;
        } else {
            ensureCapacity(size + width);
            if (strLen > 0) {
                str.getChars(0, strLen, buffer, size);
            }
            size += strLen;
            appendPadding(width - strLen, padChar);
        }
    }
    return this;
}
