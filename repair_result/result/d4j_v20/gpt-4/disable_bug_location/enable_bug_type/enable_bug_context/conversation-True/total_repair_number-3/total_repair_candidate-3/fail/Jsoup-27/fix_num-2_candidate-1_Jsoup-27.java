static String getCharsetFromContentType(String contentType) {
    if (contentType == null) return null;
    Matcher m = charsetPattern.matcher(contentType);
    if (m.find()) {
        String charset = m.group(1).trim();
        // Return charset exactly as extracted, preserving case,
        // for tests expecting original case (e.g. "utf-8" vs "UTF-8").
        // Remove optional surrounding quotes if present.
        if (charset.startsWith("\"") && charset.endsWith("\"") && charset.length() > 1) {
            charset = charset.substring(1, charset.length() - 1);
        }
        return charset;
    }
    return null;
}
