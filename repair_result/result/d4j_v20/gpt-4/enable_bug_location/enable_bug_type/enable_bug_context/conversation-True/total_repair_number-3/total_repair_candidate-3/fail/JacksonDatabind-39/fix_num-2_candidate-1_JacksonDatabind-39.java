public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // 29-Jan-2016, tatu: Simple skipping for all other tokens, but FIELD_NAME bit
    //    special unfortunately
    if (p.hasToken(JsonToken.FIELD_NAME)) {
        p.nextToken(); // move to value to skip
        p.skipChildren();
        p.nextToken(); // move past the end of the object/field
    } else {
        p.skipChildren();
    }
    return null;
}
