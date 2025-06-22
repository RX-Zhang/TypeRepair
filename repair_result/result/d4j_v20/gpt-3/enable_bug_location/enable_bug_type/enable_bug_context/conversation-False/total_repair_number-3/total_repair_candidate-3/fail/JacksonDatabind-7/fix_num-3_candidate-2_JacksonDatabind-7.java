public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    JsonToken t = jp.getCurrentToken();
    if (t == JsonToken.FIELD_NAME) {
        // Handle the special case where we start with a FIELD_NAME
        writeStartObject(); // Assume a START_OBJECT was present
        writeFieldName(jp.getCurrentName());
        t = jp.nextToken(); // Move to the next token
    }
    
    copyCurrentStructure(jp);
    return this;
}
