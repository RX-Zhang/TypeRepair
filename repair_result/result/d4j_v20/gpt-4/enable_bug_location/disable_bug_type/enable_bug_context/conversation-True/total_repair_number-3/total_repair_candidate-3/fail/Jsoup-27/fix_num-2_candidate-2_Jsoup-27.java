static String getCharsetFromContentType(String contentType) {
    if (contentType == null) return null;
    Matcher m = charsetPattern.matcher(contentType);
    if (m.find()) {
        String charset = m.group(1).trim();
        // Remove quotes if present
        if (charset.startsWith("\"") && charset.endsWith("\"") && charset.length() > 1) {
            charset = charset.substring(1, charset.length() - 1);
        }
        return charset;
    }
    return null;
}
