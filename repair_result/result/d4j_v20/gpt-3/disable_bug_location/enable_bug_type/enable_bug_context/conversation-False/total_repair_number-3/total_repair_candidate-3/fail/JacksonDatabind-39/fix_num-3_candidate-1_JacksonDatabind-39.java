public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // 29-Jan-2016, tatu: Special handling for FIELD_NAME token: don't skip it
    if (p.getCurrentToken() != null && p.getCurrentToken().isStructStart()) {
        p.skipChildren();
    }
    return null;
}
