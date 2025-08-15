public static String encodeBase64String(byte[] binaryData) {
    byte[] encoded = encodeBase64(binaryData, true);
    // Remove any trailing newline or line separator characters from the encoded byte array
    int length = encoded.length;
    while (length > 0 && (encoded[length - 1] == '\n' || encoded[length - 1] == '\r')) {
        length--;
    }
    return StringUtils.newStringUtf8(encoded, 0, length);
}
