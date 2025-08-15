public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    JsonToken t = jp.getCurrentToken();

    // Special case: if token is FIELD_NAME, assume missing START_OBJECT and insert it
    if (t == JsonToken.FIELD_NAME) {
        writeStartObject();
        copyCurrentStructure(jp);
        writeEndObject();
    } else {
        copyCurrentStructure(jp);
    }
    return this;
}
