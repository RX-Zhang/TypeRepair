static String getCharsetFromContentType(String contentType) {
    if (contentType == null) return null;
    Matcher m = charsetPattern.matcher(contentType);
    if (m.find()) {
        String charset = m.group(1).trim();
        charset = charset.toLowerCase(Locale.ENGLISH); // Change to lower case to match expected output
        return charset;
    }
    return null;
}
