public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    if (p.hasToken(JsonToken.FIELD_NAME)) {
        // skip the FIELD_NAME token but do not skip children yet
        // as we want to nullify the value of the field
        p.nextToken();
        p.skipChildren();
    } else {
        p.skipChildren();
    }
    return null;
}
