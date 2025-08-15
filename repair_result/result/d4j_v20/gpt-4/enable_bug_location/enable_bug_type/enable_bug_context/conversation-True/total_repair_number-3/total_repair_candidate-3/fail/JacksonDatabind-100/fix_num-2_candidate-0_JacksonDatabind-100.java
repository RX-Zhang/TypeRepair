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
        // If TextNode, decode using the Base64Variant
        if (n.isTextual()) {
            try {
                return b64variant.decode(n.asText());
            } catch (IllegalArgumentException iae) {
                throw new JsonParseException(this, "Cannot access contents of TextNode as binary due to broken Base64 encoding: "+iae.getMessage(), iae);
            }
        }
    }
    // otherwise return null to mark we have no binary content
    return null;
}
