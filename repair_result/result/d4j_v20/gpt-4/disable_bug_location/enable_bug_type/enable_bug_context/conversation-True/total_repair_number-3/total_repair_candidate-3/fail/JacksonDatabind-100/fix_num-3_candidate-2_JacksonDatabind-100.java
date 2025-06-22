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
        // If node is POJO, try to extract byte[]
        if (n.isPojo()) {
            Object ob = ((POJONode) n).getPojo();
            if (ob instanceof byte[]) {
                return (byte[]) ob;
            }
        }
        // If node is text, decode Base64 using given variant
        if (n.isTextual()) {
            String text = n.textValue().trim();
            if (!text.isEmpty()) {
                try {
                    return b64variant.decode(text);
                } catch (IllegalArgumentException iae) {
                    // Wrap the illegal argument exception into a JsonParseException
                    throw new JsonParseException(null,
                        "Cannot access contents of TextNode as binary due to broken Base64 encoding: " + iae.getMessage(), iae);
                }
            }
        }
    }
    // otherwise return null to mark we have no binary content
    return null;
}
