public byte[] getBinaryValue(Base64Variant b64variant)
    throws IOException, JsonParseException
{
    // Multiple possibilities...
    JsonNode n = currentNode();
    if (n != null) {
        // [databind#2096]: although `binaryValue()` works for real binary node
        // and embedded "POJO" node, coercion from TextNode may require variant, so:
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
        // If it's a text node, decode using specified Base64 variant
        if (n.isTextual()) {
            String str = n.textValue().trim();
            if (!str.isEmpty()) {
                try {
                    return b64variant.decode(str);
                } catch (IllegalArgumentException e) {
                    // Wrap exception as JsonParseException, to provide consistent error handling
                    throw new JsonParseException(null,
                        "Cannot access contents of TextNode as binary due to broken Base64 encoding: "
                        + e.getMessage(), e);
                }
            }
        }
    }
    // otherwise return null to mark we have no binary content
    return null;
}
