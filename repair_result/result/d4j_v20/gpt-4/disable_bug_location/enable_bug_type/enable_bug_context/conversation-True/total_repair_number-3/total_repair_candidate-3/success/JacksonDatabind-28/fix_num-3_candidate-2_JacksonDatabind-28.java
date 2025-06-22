public ObjectNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    if (p == null || ctxt == null) {
        throw new IllegalArgumentException("JsonParser and DeserializationContext must not be null");
    }

    switch (p.getCurrentTokenId()) {
        case JsonTokenId.ID_START_OBJECT:
            p.nextToken();
            return deserializeObject(p, ctxt, ctxt.getNodeFactory());
        case JsonTokenId.ID_FIELD_NAME:
            return deserializeObject(p, ctxt, ctxt.getNodeFactory());
        case JsonTokenId.ID_END_OBJECT:
            // Empty ObjectNode
            return ctxt.getNodeFactory().objectNode();
        default:
            throw ctxt.mappingException(ObjectNode.class);
    }
}
