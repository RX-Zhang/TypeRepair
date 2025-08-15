public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    // As per #592, need to support a special case of starting from FIELD_NAME,
    // which means that we are missing START_OBJECT but need to assume one did exist.
    if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
        writeStartObject();
        copyCurrentStructure(jp);
        writeEndObject();
    } else {
        copyCurrentStructure(jp);
    }
    return this;
}
