public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    // Handle special case: if current token is FIELD_NAME, 
    // wrap with START_OBJECT and END_OBJECT tokens (as per #592)
    JsonToken t = jp.getCurrentToken();
    if (t == JsonToken.FIELD_NAME) {
        writeStartObject();
        copyCurrentStructure(jp);
        writeEndObject();
    } else {
        copyCurrentStructure(jp);
    }
    return this;
}
