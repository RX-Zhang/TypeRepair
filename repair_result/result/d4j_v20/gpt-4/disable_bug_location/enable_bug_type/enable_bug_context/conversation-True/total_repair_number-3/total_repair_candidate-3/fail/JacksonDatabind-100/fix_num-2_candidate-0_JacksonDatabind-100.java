public byte[] getBinaryValue(Base64Variant b64variant)
    throws IOException, JsonParseException
{
    JsonNode n = currentNode();
    if (n != null) {
        byte[] data = n.binaryValue();
        if (data != null) {
            return data;
        }
        if (n.isTextual()) {
            // TextNode coercion requires decoding using variant
            String text = n.textValue().trim();
            if (text.length() == 0) {
                return new byte[0];
            }
            try {
                return b64variant.decode(text);
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(this, "Cannot access contents of TextNode as binary due to broken Base64 encoding: "+e.getMessage());
            }
        }
        if (n.isPojo()) {
            Object ob = ((POJONode) n).getPojo();
            if (ob instanceof byte[]) {
                return (byte[]) ob;
            }
        }
    }
    return null;
}
