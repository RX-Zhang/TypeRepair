public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    JsonToken t = jp.getCurrentToken();
    if (t == JsonToken.FIELD_NAME) {
        // As per #592, if we start at FIELD_NAME, inject START_OBJECT token first
        writeStartObject();
        copyCurrentStructure(jp);
        writeEndObject();
    } else {
        copyCurrentStructure(jp);
    }
    return this;
}
