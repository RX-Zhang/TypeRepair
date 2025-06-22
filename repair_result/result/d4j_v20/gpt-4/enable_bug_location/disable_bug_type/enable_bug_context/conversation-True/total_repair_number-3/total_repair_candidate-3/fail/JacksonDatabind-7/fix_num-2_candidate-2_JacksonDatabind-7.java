public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    // Check the current token; if FIELD_NAME, we wrap structure with START_OBJECT/END_OBJECT
    JsonToken t = jp.getCurrentToken();
    if (t == JsonToken.FIELD_NAME) {
        _append(JsonToken.START_OBJECT);
        copyCurrentStructure(jp);
        _append(JsonToken.END_OBJECT);
    } else {
        copyCurrentStructure(jp);
    }
    return this;
}
