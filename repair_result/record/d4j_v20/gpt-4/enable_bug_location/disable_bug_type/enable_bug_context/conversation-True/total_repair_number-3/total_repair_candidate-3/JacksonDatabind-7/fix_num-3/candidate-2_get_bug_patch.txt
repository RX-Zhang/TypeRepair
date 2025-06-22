public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
        writeStartObject();
    }
    copyCurrentStructure(jp);
    return this;
}
