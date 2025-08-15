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
        if (n.isTextual()) {
            // decode base64 text content using provided variant
            String base64Text = n.textValue();
            // decode using Base64Variant, will throw JsonParseException if invalid
            try {
                data = b64variant.decode(base64Text);
            } catch (IllegalArgumentException iae) {
                throw new JsonParseException(null,
                    "Cannot access contents of TextNode as binary due to broken Base64 encoding: " + iae.getMessage(), iae);
            }
            return data;
        }
    }
    // otherwise return null to mark we have no binary content
    return null;
}
