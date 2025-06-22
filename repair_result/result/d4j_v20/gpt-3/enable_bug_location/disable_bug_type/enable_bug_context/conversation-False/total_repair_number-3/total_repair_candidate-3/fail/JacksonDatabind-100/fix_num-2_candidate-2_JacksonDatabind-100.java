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
        if (n.isTextual()) {
            // Use Base64 decoding on the textual content
            String text = n.textValue().trim();
            try {
                return b64variant.decode(text);
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(
                    null,
                    "Cannot access contents of TextNode as binary due to broken Base64 encoding: " + e.getMessage(),
                    e
                );
            }
        }
    }
    return null;
}
