public byte[] getBinaryValue(Base64Variant b64variant)
    throws IOException, JsonParseException
{
    // Multiple possibilities...
    JsonNode n = currentNode();
    if (n != null) {
        // [databind#2096]: although `binaryValue()` works for real binary node
        // and embedded "POJO" node, coercion from TextNode may require variant, so:
        if (n instanceof TextNode) {
            // Handle TextNode specifically for Base64 encoding
            String base64Content = n.asText();
            try {
                return b64variant.decode(base64Content);
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(null, "Cannot access contents of TextNode as binary due to broken Base64 encoding: " + e.getMessage());
            }
        }
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
    }
    // otherwise return null to mark we have no binary content
    return null;
}
