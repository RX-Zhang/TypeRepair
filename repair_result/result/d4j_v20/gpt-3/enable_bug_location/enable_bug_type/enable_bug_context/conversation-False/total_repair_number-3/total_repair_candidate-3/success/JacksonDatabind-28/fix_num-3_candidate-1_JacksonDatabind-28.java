public ObjectNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    JsonToken current = p.getCurrentToken();
    if (current == JsonToken.START_OBJECT) {
        p.nextToken();
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    }
    // 23-Sep-2015, tatu: Ugh. We may also be given FIELD_NAME (for non-empty object), 
    // or possibly END_OBJECT (for empty object) if caller advanced already.
    if (current == JsonToken.FIELD_NAME) {
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    }
    if (current == JsonToken.END_OBJECT) {
        // Empty Object: create empty ObjectNode
        return ctxt.getNodeFactory().objectNode();
    }
    throw ctxt.mappingException(ObjectNode.class);
 }
