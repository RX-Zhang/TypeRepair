public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    // If starting token is FIELD_NAME, simulate START_OBJECT before copying the structure
    if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
        writeStartObject();
        copyCurrentStructure(jp);
        writeEndObject();
    } else {
        copyCurrentStructure(jp);
    }
    return this;
}
