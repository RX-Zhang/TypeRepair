public static String encodeBase64String(byte[] binaryData) {
    byte[] encoded = encodeBase64(binaryData, true);
    // Remove trailing new line characters that encodeBase64 might add
    int len = encoded.length;
    while (len > 0 && (encoded[len - 1] == '\n' || encoded[len - 1] == '\r')) {
        len--;
    }
    return StringUtils.newStringUtf8(encoded, 0, len);
}
