public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    // Special case: if parser is at FIELD_NAME, inject START_OBJECT before copying structure
    if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
        writeStartObject();
        copyCurrentStructure(jp);
        writeEndObject();
    } else {
        copyCurrentStructure(jp);
    }
    return this;
}
