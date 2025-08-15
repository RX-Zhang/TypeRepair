public static String encodeBase64String(byte[] binaryData) {
    byte[] encoded = encodeBase64(binaryData, true);
    return encoded == null ? null : StringUtils.newStringUtf8(encoded);
}
