public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // If current token is a field name (start of an object), skip it using the type deserializer
    if (p.getCurrentToken() == JsonToken.FIELD_NAME || p.getCurrentToken() == JsonToken.START_OBJECT) {
        p.skipChildren();
        return null;
    }
    p.skipChildren();
    return null;
}
