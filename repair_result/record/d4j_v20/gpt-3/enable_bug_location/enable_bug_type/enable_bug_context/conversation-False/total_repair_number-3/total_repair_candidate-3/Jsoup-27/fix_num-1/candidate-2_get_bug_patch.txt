static String getCharsetFromContentType(String contentType) {
    if (contentType == null) return null;
    Matcher m = charsetPattern.matcher(contentType);
    if (m.find()) {
        String charset = m.group(1).trim();
        // Change to lower case as test cases expect "utf-8" instead of "UTF-8"
        charset = charset.toLowerCase(Locale.ENGLISH);
        return charset;
    }
    return null;
}
