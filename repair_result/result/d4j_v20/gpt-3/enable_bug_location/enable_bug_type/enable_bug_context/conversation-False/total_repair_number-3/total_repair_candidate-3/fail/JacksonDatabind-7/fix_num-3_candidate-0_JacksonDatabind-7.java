public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    JsonToken t = jp.getCurrentToken();
    if (t == JsonToken.FIELD_NAME) {
        // Handle the special case where we start from FIELD_NAME
        // Assume a START_OBJECT was present
        writeStartObject();
        writeFieldName(jp.getCurrentName());
        t = jp.nextToken(); // Move to the next token
    }
    
    copyCurrentStructure(jp);
    return this;
}
