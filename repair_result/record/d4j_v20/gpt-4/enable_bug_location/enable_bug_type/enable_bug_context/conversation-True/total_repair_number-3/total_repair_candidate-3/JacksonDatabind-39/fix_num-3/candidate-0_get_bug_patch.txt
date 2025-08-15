public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // Delegate to deserializeWithType to handle potential type info properly, 
    // or skip children if no type info is needed
    if (p.hasToken(JsonToken.FIELD_NAME) || p.hasToken(JsonToken.START_OBJECT) || p.hasToken(JsonToken.START_ARRAY)) {
        return deserializeWithType(p, ctxt, null);
    }
    p.skipChildren();
    return null;
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
