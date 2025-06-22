public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    if (p.hasToken(JsonToken.FIELD_NAME)) {
        // if current token is a field name, just move to next token
        p.nextToken();
        // then skip children of that token if any
        p.skipChildren();
    } else {
        // for all other tokens, just skip children if any
        p.skipChildren();
    }
    return null;
}
