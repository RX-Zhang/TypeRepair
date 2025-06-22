public ObjectNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    if (p.getCurrentToken() == null) {
        throw ctxt.mappingException(ObjectNode.class);
    }

    switch (p.getCurrentTokenId()) {
    case JsonTokenId.ID_START_OBJECT:
        p.nextToken();
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    case JsonTokenId.ID_FIELD_NAME:
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    case JsonTokenId.ID_END_OBJECT:
        // Handle empty object case appropriately
        return ctxt.getNodeFactory().objectNode();
    default:
        throw ctxt.mappingException(ObjectNode.class);
    }
}
