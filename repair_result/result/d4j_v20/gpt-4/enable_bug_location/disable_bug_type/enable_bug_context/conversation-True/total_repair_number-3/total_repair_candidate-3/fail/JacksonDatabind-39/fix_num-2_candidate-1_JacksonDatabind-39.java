public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    if (p.hasToken(JsonToken.START_OBJECT) || p.hasToken(JsonToken.START_ARRAY) || p.hasToken(JsonToken.FIELD_NAME)) {
        p.skipChildren();
    }
    return null;
}
