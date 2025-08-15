static String getCharsetFromContentType(String contentType) {
    if (contentType == null) return null;
    Matcher m = charsetPattern.matcher(contentType);
    if (m.find()) {
        String charset = m.group(1).trim();
        // Do not convert charset to upper case to preserve original casing as expected by tests
        return charset;
    }
    return null;
}
