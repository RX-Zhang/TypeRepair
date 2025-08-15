static String getCharsetFromContentType(String contentType) {
    if (contentType == null) return null;
    Matcher m = charsetPattern.matcher(contentType);
    if (m.find()) {
        String charset = m.group(1).trim();
        // Convert to lower case instead of upper case to match expected output
        charset = charset.toLowerCase(Locale.ENGLISH);
        return charset;
    }
    return null;
}
