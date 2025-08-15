public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // 29-Jan-2016, tatu: Field name tokens need special handling; skip all children for other tokens.
    if (p.getCurrentToken() == JsonToken.FIELD_NAME) {
        // do not advance past FIELD_NAME; leave parser at this token
        // so caller can handle it properly
        return null;
    }
    p.skipChildren();
    return null;
}
