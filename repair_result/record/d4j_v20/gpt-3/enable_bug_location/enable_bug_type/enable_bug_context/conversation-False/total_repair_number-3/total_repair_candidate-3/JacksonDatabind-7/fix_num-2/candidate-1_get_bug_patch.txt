public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    JsonToken t = jp.getCurrentToken();
    if (t == JsonToken.FIELD_NAME) {
        // If we start with a FIELD_NAME, we assume a START_OBJECT was expected
        writeStartObject();
    }
    copyCurrentStructure(jp);
    return this;
}
