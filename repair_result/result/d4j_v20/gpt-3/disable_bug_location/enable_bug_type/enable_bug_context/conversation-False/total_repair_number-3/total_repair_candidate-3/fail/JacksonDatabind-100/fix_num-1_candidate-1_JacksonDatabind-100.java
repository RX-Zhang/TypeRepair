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
            // decode Base64 using the given variant from the textual content
            String text = n.textValue().trim();
            if (text.length() == 0) {
                // empty String -> empty byte array
                return new byte[0];
            }
            return b64variant.decode(text);
        }
    }
    // otherwise return null to mark we have no binary content
    return null;
}
