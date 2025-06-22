public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    if (p.getCurrentToken() == JsonToken.FIELD_NAME) {
        // skip FIELD_NAME token itself and then skip its value (children)
        p.nextToken();
        p.skipChildren();
    } else {
        p.skipChildren();
    }
    return null;
}
