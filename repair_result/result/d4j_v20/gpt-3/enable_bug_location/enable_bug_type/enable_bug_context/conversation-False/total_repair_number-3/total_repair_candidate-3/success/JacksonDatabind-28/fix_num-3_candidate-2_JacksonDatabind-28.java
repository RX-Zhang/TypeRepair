public ObjectNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    JsonToken currentToken = p.getCurrentToken();

    if (currentToken == JsonToken.START_OBJECT) {
        p.nextToken();
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    }
    // Handle empty object {} where the current token might be END_OBJECT immediately after START_OBJECT
    if (currentToken == JsonToken.END_OBJECT) {
        return ctxt.getNodeFactory().objectNode();
    }
    if (currentToken == JsonToken.FIELD_NAME) {
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    }
    throw ctxt.mappingException(ObjectNode.class);
}
