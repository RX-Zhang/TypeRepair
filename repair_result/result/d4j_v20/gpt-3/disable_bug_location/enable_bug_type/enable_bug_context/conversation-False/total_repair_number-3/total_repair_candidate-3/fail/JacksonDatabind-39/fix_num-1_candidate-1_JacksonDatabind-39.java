public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // 29-Jan-2016, tatu: Simple skipping for all tokens except FIELD_NAME,
    // which needs special handling for skipping children properly.
    
    if (p.currentToken() == JsonToken.FIELD_NAME) {
        // Move to the value token of the field to skip children correctly
        p.nextToken();
    }
    p.skipChildren();
    return null;
}
