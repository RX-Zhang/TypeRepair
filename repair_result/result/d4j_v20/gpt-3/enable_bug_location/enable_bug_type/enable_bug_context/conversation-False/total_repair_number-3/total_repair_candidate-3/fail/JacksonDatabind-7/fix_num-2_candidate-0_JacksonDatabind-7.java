public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    JsonToken t = jp.getCurrentToken();
    if (t == JsonToken.FIELD_NAME) {
        // Handle the special case where we start from FIELD_NAME
        // This means we assume a START_OBJECT was present
        jp.nextToken(); // Move to the value associated with the field name
    }
    copyCurrentStructure(jp);
    return this;
}
