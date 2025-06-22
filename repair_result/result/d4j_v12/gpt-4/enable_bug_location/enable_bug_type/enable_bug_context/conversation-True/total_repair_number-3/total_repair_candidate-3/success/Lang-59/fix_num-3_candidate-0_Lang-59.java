public StrBuilder appendFixedWidthPadRight(Object obj, int width, char padChar) {
    if (width > 0) {
        String str = (obj == null ? getNullText() : obj.toString());
        if (str == null) {
            str = "";
        }
        int strLen = str.length();
        ensureCapacity(size + width);
        if (strLen >= width) {
            str.getChars(0, width, buffer, size);
            size += width;
        } else {
            str.getChars(0, strLen, buffer, size);
            size += strLen;
            appendPadding(width - strLen, padChar);
        }
    }
    return this;
}
