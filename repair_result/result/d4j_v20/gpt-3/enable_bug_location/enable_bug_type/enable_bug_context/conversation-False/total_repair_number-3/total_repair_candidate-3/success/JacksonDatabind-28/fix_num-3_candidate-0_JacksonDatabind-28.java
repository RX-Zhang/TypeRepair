public ObjectNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    JsonToken curr = p.getCurrentToken();
    if (curr == JsonToken.START_OBJECT) {
        p.nextToken();
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    }
    // 23-Sep-2015, tatu: Ugh. We may also be given FIELD_NAME (start of first field)
    //    or END_OBJECT if caller has advanced to the first token of Object, but for empty Object
    if (curr == JsonToken.FIELD_NAME || curr == JsonToken.END_OBJECT) {
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    }
    throw ctxt.mappingException(ObjectNode.class);
}
