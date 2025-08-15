public byte[] getBinaryValue(Base64Variant b64variant)
    throws IOException, JsonParseException
{
    JsonNode n = currentNode();
    if (n != null) {
        byte[] data = n.binaryValue();
        if (data != null) {
            return data;
        }
        if (n.isPojo()) {
            Object ob = ((POJONode) n).getPojo();
            if (ob instanceof byte[]) {
                return (byte[]) ob;
            }
        }
        if (n.isTextual()) {
            // decode textual content using the provided Base64 variant
            String str = n.textValue();
            if (str == null) {
                return null;
            }
            try {
                return b64variant.decode(str);
            } catch (IllegalArgumentException iae) {
                throw new JsonParseException(null, "Cannot access contents of TextNode as binary due to broken Base64 encoding: " + iae.getMessage(), iae);
            }
        }
    }
    return null;
}
