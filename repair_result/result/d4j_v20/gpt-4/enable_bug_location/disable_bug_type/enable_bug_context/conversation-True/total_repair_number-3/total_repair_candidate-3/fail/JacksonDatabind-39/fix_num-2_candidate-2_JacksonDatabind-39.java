public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // Use deserializeWithType to handle type information properly, skipping unknown types safely
    return deserializeWithType(p, ctxt, null);
}

public Object deserializeWithType(JsonParser p, DeserializationContext ctxt,
        TypeDeserializer typeDeserializer) throws IOException
{
    // Not sure if we need to bother but:

    switch (p.getCurrentTokenId()) {
    case JsonTokenId.ID_START_ARRAY:
    case JsonTokenId.ID_START_OBJECT:
    case JsonTokenId.ID_FIELD_NAME:
        if (typeDeserializer != null) {
            return typeDeserializer.deserializeTypedFromAny(p, ctxt);
        }
        // If no typeDeserializer given, just skip children and return null
        p.skipChildren();
        return null;
    default:
        return null;
    }
}
