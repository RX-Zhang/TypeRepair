public ObjectNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    JsonToken currentToken = p.getCurrentToken();

    if (currentToken == JsonToken.START_OBJECT) {
        // Move to the first token inside the object
        JsonToken nextToken = p.nextToken();
        if (nextToken == JsonToken.END_OBJECT) {
            // Empty object, return empty ObjectNode
            return ctxt.getNodeFactory().objectNode();
        }
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    }
    // According to the comment: FIELD_NAME token may occur for empty objects
    if (currentToken == JsonToken.FIELD_NAME) {
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    }
    if (currentToken == JsonToken.END_OBJECT) {
        // Empty object, return empty ObjectNode
        return ctxt.getNodeFactory().objectNode    }
    throw ctxt.mappingException(ObjectNode.class);
}
