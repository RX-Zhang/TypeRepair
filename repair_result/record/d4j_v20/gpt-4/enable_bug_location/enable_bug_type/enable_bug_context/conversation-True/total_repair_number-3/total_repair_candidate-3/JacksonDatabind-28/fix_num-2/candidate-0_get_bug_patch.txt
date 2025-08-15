public ObjectNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    switch (p.getCurrentTokenId()) {
    case JsonTokenId.ID_START_OBJECT:
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    case JsonTokenId.ID_START_ARRAY:
        // Should not occur here if method only returns ObjectNode, but included for completeness
        throw ctxt.mappingException(ObjectNode.class);
    default:
        throw ctxt.mappingException(ObjectNode.class);
    }
}
