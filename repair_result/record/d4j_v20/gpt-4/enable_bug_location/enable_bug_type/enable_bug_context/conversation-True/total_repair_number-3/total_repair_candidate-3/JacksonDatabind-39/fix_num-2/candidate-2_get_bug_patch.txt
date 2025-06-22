public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    p.skipChildren();
    return null;
}

public Object deserializeWithType(JsonParser p, DeserializationContext ctxt,
        TypeDeserializer typeDeserializer) throws IOException
{
    switch (p.getCurrentTokenId()) {
    case JsonTokenId.ID_START_ARRAY:
    case JsonTokenId.ID_START_OBJECT:
    case JsonTokenId.ID_FIELD_NAME:
        return typeDeserializer.deserializeTypedFromAny(p, ctxt);
    default:
        return null;
    }
}
