static String getCharsetFromContentType(String contentType) {
    if (contentType == null) return null;
    Matcher m = charsetPattern.matcher(contentType);
    if (m.find()) {
        String charset = m.group(1).trim();
        // Change to lowercase to match expected output in test cases
        charset = charset.toLowerCase(Locale.ENGLISH);
        return charset;
    }
    return null;
}
