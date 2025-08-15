public static String encodeBase64String(byte[] binaryData) {
    if (binaryData == null) {
        return null; // Handle null input
    }
    return StringUtils.newStringUtf8(encodeBase64(binaryData, true));
}
