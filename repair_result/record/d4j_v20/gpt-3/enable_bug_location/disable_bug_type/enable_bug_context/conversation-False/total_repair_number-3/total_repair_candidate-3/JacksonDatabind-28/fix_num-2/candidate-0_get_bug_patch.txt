public ObjectNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    if (p == null) {
        throw new IllegalArgumentException("JsonParser cannot be null");
    }
    
    if (p.getCurrentToken() == JsonToken.START_OBJECT) {
        p.nextToken();
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    }
    
    // 23-Sep-2015, tatu: Ugh. We may also be given END_OBJECT (similar to FIELD_NAME),
    //    if caller has advanced to the first token of Object, but for empty Object
    if (p.getCurrentToken() == JsonToken.FIELD_NAME) {
        return deserializeObject(p, ctxt, ctxt.getNodeFactory());
    }
    
    // Adding exception handling for unexpected tokens
    if (p.getCurrentToken() == JsonToken.END_OBJECT) {
        throw ctxt.mappingException("Cannot deserialize instance of " + ObjectNode.class.getName() + " out of END_OBJECT token");
    }
    
    throw ctxt.mappingException(ObjectNode.class);
}
