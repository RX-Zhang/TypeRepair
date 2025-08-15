public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    if (p.getCurrentToken() == JsonToken.FIELD_NAME) {
        p.nextToken();
        p.skipChildren();
        p.nextToken();
    } else {
        p.skipChildren();
    }
    return null;
}
