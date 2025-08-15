public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    // Special handling when starting at FIELD_NAME: need to wrap structure within an implicit START_OBJECT
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
