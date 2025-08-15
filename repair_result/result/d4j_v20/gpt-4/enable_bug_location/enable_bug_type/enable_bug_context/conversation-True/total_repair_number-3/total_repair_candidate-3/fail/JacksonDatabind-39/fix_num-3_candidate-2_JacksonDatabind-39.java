public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // Delegate to deserializeWithType to handle polymorphic types properly,
    // skipping unknown types correctly without failing.
    return deserializeWithType(p, ctxt, null);
}

public Object deserializeWithType(JsonParser p, DeserializationContext ctxt,
        TypeDeserializer typeDeserializer) throws IOException
{
    if (typeDeserializer == null) {
        // skip children if no type deserializer is provided, to avoid errors on unknown types
        p.skipChildren();
        return null;
    }

    switch (p.getCurrentTokenId()) {
    case JsonTokenId.ID_START_ARRAY:
    case JsonTokenId.ID_START_OBJECT:
    case JsonTokenId.ID_FIELD_NAME:
        return typeDeserializer.deserializeTypedFromAny(p, ctxt);
    default:
        return null;
    }
}
