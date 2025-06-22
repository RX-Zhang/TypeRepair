public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    // Check if the current token is FIELD_NAME, indicating a missing START_OBJECT
    if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
        // If so, we can assume a START_OBJECT was expected and handle it accordingly
        writeStartObject();
        // Copy the current structure, starting from the FIELD_NAME
        copyCurrentStructure(jp);
        writeEndObject();
    } else {
        // If not FIELD_NAME, just copy the current structure as is
        copyCurrentStructure(jp);
    }
    return this;
}
