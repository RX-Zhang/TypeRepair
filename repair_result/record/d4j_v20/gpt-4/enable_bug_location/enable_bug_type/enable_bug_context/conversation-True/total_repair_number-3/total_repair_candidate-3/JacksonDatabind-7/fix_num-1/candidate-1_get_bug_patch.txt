public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    JsonToken t = jp.getCurrentToken();

    // As per #592, special case for FIELD_NAME token (missing START_OBJECT)
    if (t == JsonToken.FIELD_NAME) {
        writeStartObject();
        copyCurrentStructure(jp);
        writeEndObject();
    } else {
        copyCurrentStructure(jp);
    }
    return this;
}
