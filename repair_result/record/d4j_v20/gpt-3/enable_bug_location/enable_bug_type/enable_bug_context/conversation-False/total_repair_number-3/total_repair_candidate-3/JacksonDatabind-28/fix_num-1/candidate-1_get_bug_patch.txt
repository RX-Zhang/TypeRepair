public ObjectNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    if (p.getCurrentToken() == JsonToken.START_OBJECT) {
        p.nextToken();
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    }
    // 23-Sep-2015, tatu: Ugh. We may also be given FIELD_NAME (similar to START_OBJECT),
    //    if caller has advanced to the first token of Object, but for empty Object
    if (p.getCurrentToken() == JsonToken.FIELD_NAME) {
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    }
    // Allow for empty objects represented by END_OBJECT token without error
    if (p.getCurrentToken() == JsonToken.END_OBJECT) {
        return ctxt.getNodeFactory().objectNode();
    }
    throw ctxt.mappingException(ObjectNode.class);
}
