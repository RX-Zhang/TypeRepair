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
        // Handle TextNode: decode text using the provided Base64Variant
        if (n.isTextual()) {
            String encoded = n.textValue();
            if (encoded == null) {
                return null;
            }
            try {
                return b64variant.decode(encoded);
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(null, "Cannot access contents of TextNode as binary due to broken Base64 encoding: "+e.getMessage(), e);
            }
        }
    }
    // otherwise return null to mark we have no binary content
    return null;
}
