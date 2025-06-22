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
        // If node is a text node, decode base64 with given variant
        if (n.isTextual()) {
            String text = n.textValue();
            if (text == null) {
                return null;
            }
            try {
                return b64variant.decode(text);
            } catch (IllegalArgumentException iae) {
                // Convert to JsonParseException to indicate decoding problem
                throw new JsonParseException(null, "Cannot access contents of TextNode as binary due to broken Base64 encoding: "+iae.getMessage(), iae);
            }
        }
    }
    // otherwise return null to mark we have no binary content
    return null;
}
