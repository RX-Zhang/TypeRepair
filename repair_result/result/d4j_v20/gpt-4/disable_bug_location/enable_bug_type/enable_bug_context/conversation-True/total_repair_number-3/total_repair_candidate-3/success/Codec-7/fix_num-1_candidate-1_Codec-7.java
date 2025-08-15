public static String encodeBase64String(byte[] binaryData) {
    if (binaryData == null) {
        return null;
    }
    return new Base64().encodeToString(binaryData);
}
