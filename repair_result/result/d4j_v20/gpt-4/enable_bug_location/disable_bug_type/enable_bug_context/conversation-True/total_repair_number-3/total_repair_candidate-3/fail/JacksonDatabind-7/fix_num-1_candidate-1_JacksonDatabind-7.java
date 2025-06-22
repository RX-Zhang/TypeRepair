public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    // If the current token is FIELD_NAME, we treat it as if START_OBJECT was missing
    if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
        writeStartObject();
        copyCurrentStructure(jp);
        writeEndObject();
    } else {
        copyCurrentStructure(jp);
    }
    return this;
}
