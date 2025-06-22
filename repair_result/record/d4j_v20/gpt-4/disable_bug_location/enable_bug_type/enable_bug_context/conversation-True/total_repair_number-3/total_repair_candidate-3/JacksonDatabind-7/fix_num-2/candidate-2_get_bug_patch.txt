public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
{
    JsonToken t = jp.getCurrentToken();

    // As per #592, if starting from FIELD_NAME, assume START_OBJECT is missing and writeStartObject()
    if (t == JsonToken.FIELD_NAME) {
        writeStartObject();
    }

    copyCurrentStructure(jp);

    if (t == JsonToken.FIELD_NAME) {
        writeEndObject();
    }

    return this;
}
