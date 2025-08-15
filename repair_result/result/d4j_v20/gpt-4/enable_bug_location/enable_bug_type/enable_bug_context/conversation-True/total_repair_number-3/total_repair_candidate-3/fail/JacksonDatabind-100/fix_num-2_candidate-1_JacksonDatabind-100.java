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
        // If node is a TextNode, decode the base64 textual content using variant
        if (n.isTextual()) {
            String str = n.textValue();
            if (str != null) {
                return b64variant.decode(str);
            }
        }
    }
    // otherwise return null to mark we have no binary content
    return null;
}
