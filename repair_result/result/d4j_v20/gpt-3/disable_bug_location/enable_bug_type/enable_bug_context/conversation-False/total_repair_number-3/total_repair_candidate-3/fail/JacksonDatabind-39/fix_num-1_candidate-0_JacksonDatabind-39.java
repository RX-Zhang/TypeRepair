public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    if (p.hasToken(JsonToken.FIELD_NAME)) {
        String fieldName = p.getCurrentName();
        p.nextToken(); // move to value of the FIELD_NAME token
        p.skipChildren();
        p.nextToken(); // move to next token after the skipped value
    } else {
        p.skipChildren();
    }
    return null;
}
