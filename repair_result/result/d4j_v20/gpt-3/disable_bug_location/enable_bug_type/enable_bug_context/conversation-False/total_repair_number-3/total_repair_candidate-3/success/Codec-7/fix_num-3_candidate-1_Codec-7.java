public static String encodeBase64String(byte[] binaryData) {
    // Disable chunking by passing false to encodeBase64 to avoid trailing new lines
    return binaryData == null ? null : StringUtils.newStringUtf8(encodeBase64(binaryData, false));
}
