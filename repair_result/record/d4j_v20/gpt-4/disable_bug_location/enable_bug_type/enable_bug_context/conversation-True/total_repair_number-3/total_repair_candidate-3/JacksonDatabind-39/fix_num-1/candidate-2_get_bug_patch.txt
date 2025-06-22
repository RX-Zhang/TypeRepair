public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    if (p.getCurrentTokenId() == JsonTokenId.ID_FIELD_NAME) {
        // Do not skip children if current token is FIELD_NAME; handle specially if needed:
        return null;
    }
    p.skipChildren();
    return null;
}
