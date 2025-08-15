public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    JsonToken t = jp.getCurrentToken();
    if (t == JsonToken.FIELD_NAME) {
        // Handle case where input starts at FIELD_NAME (missing START_OBJECT)
        writeStartObject();
        copyCurrentStructure(jp);
        writeEndObject();
    } else {
        copyCurrentStructure(jp);
    }
    return this;
}
