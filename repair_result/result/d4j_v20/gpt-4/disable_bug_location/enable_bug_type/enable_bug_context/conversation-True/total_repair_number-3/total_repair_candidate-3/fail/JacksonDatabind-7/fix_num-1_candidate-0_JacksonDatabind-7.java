public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    // According to the comment: if the current token is FIELD_NAME,
    // assume a missing START_OBJECT and insert it before copying the structure.
    if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
        writeStartObject();
    }
    copyCurrentStructure(jp);
    if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
        writeEndObject();
    }
    return this;
}
