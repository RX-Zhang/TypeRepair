static String getCharsetFromContentType(String contentType) {
    if (contentType == null) return null;
    Matcher m = charsetPattern.matcher(contentType);
    if (m.find()) {
        String charset = m.group(1).trim();
        // Remove surrounding quotes if present
        if ((charset.startsWith("\"") && charset.endsWith("\"")) || (charset.startsWith("'") && charset.endsWith("'"))) {
            charset = charset.substring(1, charset.length() - 1).trim();
        }
        // Return as-is, preserving case to match expected test output
        return charset;
    }
    return null;
}
