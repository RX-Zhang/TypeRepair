public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    JsonToken t = jp.getCurrentToken();
    
    // Handle the special case where we start from FIELD_NAME
    if (t == JsonToken.FIELD_NAME) {
        // If we have a field name, we need to assume a START_OBJECT was present
        writeStartObject();
        writeFieldName(jp.getCurrentName());
        t = jp.nextToken(); // Move to the associated value
    }

    copyCurrentStructure(jp);
    
    return this;
}
