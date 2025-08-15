public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // Use deserializeWithType to properly handle polymorphic types and unknown type ids
    return deserializeWithType(p, ctxt, null);
}

public Object deserializeWithType(JsonParser p, DeserializationContext ctxt,
        TypeDeserializer typeDeserializer) throws IOException
{
    if (typeDeserializer == null) {
        // If no TypeDeserializer provided, just skip contents and return null
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
