public JsonNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    switch (p.getCurrentTokenId()) {
    case JsonTokenId.ID_START_OBJECT:
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    case JsonTokenId.ID_START_ARRAY:
        return deserializeArray(p, ctxt, ctxt.getNodeFactory());
    default:
        return deserializeAny(p, ctxt, ctxt.getNodeFactory());
    }
}
