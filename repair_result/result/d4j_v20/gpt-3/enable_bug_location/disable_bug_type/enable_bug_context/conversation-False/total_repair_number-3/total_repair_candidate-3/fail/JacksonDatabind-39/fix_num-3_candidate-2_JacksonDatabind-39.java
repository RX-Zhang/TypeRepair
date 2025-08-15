public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // 29-Jan-2016, tatu: Simple skipping for all tokens,
    // including field names to avoid errors on unrecognized fields
    while (p.currentToken() != null) {
        p.skipChildren();
        if (p.nextToken() == null) {
            break;
        }
    }
    return null;
}
