public static String encodeBase64String(byte[] binaryData) {
    // Passing false for lineBreaks to avoid inserting newlines in the Base64 output string
    return StringUtils.newStringUtf8(encodeBase64(binaryData, false));
}
