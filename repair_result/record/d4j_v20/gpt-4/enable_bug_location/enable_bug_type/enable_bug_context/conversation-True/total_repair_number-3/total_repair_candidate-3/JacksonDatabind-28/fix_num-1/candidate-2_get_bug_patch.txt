public ObjectNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    switch (p.getCurrentTokenId()) {
        case JsonTokenId.ID_START_OBJECT:
            return deserializeObject(p, ctxt, ctxt.getNodeFactory());
        case JsonTokenId.ID_END_OBJECT:
            // empty object case
            return ctxt.getNodeFactory().objectNode();
        case JsonTokenId.ID_FIELD_NAME:
            return deserializeObject(p, ctxt, ctxt.getNodeFactory());
        default:
            throw ctxt.mappingException(ObjectNode.class);
    }
}
