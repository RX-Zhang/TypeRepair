public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    JsonToken t = jp.getCurrentToken();
    // Check for FIELD_NAME token, indicating we might have missed START_OBJECT
    if (t == JsonToken.FIELD_NAME) {
        // If we are at a FIELD_NAME, we can assume a START_OBJECT should have been there
        writeStartObject();
        copyCurrentStructure(jp);
        writeEndObject();
    } else {
        copyCurrentStructure(jp);
    }
    return this;
}
