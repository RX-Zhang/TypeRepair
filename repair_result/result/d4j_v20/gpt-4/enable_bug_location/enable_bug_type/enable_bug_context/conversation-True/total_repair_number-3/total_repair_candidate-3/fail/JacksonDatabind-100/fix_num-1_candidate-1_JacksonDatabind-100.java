public byte[] getBinaryValue(Base64Variant b64variant)
    throws IOException, JsonParseException
{
    JsonNode n = currentNode();
    if (n != null) {
        // If node is binary, return binary value
        byte[] data = n.binaryValue();
        if (data != null) {
            return data;
        }
        // If node is POJO that wraps byte[], return it
        if (n.isPojo()) {
            Object ob = ((POJONode) n).getPojo();
            if (ob instanceof byte[]) {
                return (byte[]) ob;
            }
        }
        // If node is text, decode using Base64Variant
        if (n.isTextual()) {
            String str = n.textValue();
            if (str == null) {
                return null;
            }
            try {
                return b64variant.decode(str);
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(this, "Cannot access contents of TextNode as binary due to broken Base64 encoding: "+e.getMessage(), e);
            }
        }
    }
    return null;
}
