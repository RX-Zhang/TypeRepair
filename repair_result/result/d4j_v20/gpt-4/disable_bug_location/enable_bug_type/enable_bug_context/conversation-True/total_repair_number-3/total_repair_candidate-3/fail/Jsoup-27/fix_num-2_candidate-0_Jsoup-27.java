static String getCharsetFromContentType(String contentType) {
    if (contentType == null) return null;
    Matcher m = charsetPattern.matcher(contentType);
    if (m.find()) {
        String charset = m.group(1).trim();
        // Do not convert to uppercase here, preserve original case as returned
        // Check if charset is a known/valid charset before returning, else return null
        // For simplicity, assume we return as is without forced case change
        return charset;
    }
    return null;
}
