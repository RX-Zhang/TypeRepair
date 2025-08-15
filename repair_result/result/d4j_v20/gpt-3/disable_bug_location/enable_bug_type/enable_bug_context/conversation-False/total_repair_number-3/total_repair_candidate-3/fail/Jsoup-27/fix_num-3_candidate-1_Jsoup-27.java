static String getCharsetFromContentType(String contentType) {
    if (contentType == null) return null;
    Matcher m = charsetPattern.matcher(contentType);
    if (m.find()) {
        String charset = m.group(1).trim();
        // Remove optional surrounding quotes if present
        if (charset.length() > 1 && 
            ((charset.startsWith("\"") && charset.endsWith("\"")) || 
             (charset.startsWith("'") && charset.endsWith("'")))) {
            charset = charset.substring(1, charset.length() - 1).trim();
        }
        // Return charset as-is (preserve case) for exact test matching
        // Also validate the charset, return null if unknown or unsupported could be done here if needed
        return charset;
    }
    return null;
}
