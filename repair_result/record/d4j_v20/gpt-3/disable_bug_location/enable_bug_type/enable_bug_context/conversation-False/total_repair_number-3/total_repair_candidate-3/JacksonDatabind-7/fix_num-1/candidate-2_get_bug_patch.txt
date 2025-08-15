public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    JsonToken t = jp.getCurrentToken();

    if (t == JsonToken.FIELD_NAME) {
        // Simulate start of object before field name
        writeStartObject();
        copyCurrentStructure(jp);
        writeEndObject();
    } else {
        copyCurrentStructure(jp);
    }

    return this;
}
