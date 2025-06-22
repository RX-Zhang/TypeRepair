public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    // Check if the current token is FIELD_NAME, indicating a missing START_OBJECT
    if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
        // If it is a FIELD_NAME, we need to handle it as if we had a START_OBJECT
        writeStartObject(); // Start the object context
        writeFieldName(jp.getCurrentName()); // Write the field name
        jp.nextToken(); // Move to the next token
    } else {
        // If not FIELD_NAME, we can directly copy the current structure
        copyCurrentStructure(jp);
    }
    
    return this;
}
