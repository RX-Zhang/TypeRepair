static String getCharsetFromContentType(String contentType) {
    if (contentType == null) return null;
    Matcher m = charsetPattern.matcher(contentType);
    if (m.find()) {
        String charset = m.group(1).trim();
        // Do not convert to uppercase; return charset as is, preserving case
        return charset;
    }
    return null;
}
