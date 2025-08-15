public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    // Check if the current token is FIELD_NAME, meaning START_OBJECT is missing but assumed
    if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
        writeStartObject();
        copyCurrentStructure(jp);
        writeEndObject();
    } else {
        copyCurrentStructure(jp);
    }
    return this;
}
