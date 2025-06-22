public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // Use deserializeWithType to handle polymorphic types properly
    return deserializeWithType(p, ctxt, ctxt.getTypeDeserializer(null));
}

public Object deserializeWithType(JsonParser p, DeserializationContext ctxt,
        TypeDeserializer typeDeserializer) throws IOException
{
    // Not sure if we need to bother but:

    switch (p.getCurrentTokenId()) {
    case JsonTokenId.ID_START_ARRAY:
    case JsonTokenId.ID_START_OBJECT:
    case JsonTokenId.ID_FIELD_NAME:
        return typeDeserializer.deserializeTypedFromAny(p, ctxt);
    default:
        return null;
    }
}
