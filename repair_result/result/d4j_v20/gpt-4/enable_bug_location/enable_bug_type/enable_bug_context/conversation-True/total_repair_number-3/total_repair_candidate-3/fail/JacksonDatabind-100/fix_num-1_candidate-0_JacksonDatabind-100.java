public byte[] getBinaryValue(Base64Variant b64variant)
    throws IOException, JsonParseException
{
    JsonNode n = currentNode();
    if (n != null) {
        // If node is a binary node or POJO wrapping bytes, handle as before
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
        // If node is a text node, decode using the provided Base64 variant
        if (n.isTextual()) {
            String base64Text = n.textValue();
            try {
                return b64variant.decode(base64Text);
            } catch (IllegalArgumentException iae) {
                throw new JsonParseException("Cannot access contents of TextNode as binary due to broken Base64 encoding: " + iae.getMessage(), null, iae);
            }
        }
    }
    // otherwise return null to mark we have no binary content
    return null;
}
