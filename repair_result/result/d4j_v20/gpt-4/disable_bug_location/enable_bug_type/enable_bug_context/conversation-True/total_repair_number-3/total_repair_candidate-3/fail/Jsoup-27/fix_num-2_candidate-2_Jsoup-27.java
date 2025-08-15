static String getCharsetFromContentType(String contentType) {
    if (contentType == null) return null;
    Matcher m = charsetPattern.matcher(contentType);
    if (m.find()) {
        String charset = m.group(1).trim();
        // return charset as-is to preserve case sensitivity as expected by tests
        return charset;
    }
    return null;
}
