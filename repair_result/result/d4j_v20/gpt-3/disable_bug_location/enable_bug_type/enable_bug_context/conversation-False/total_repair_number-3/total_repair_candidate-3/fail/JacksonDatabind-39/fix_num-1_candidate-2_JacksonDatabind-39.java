public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    if (p.getCurrentToken() == JsonToken.FIELD_NAME) {
        p.nextToken();
    }
    p.skipChildren();
    return null;
}
