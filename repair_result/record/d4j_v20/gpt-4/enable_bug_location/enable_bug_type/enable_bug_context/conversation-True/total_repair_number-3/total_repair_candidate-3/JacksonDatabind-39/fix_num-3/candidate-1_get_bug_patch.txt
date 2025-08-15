public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // Use the deserializeWithType method to properly handle polymorphic types
    return deserializeWithType(p, ctxt, null);
}

public Object deserializeWithType(JsonParser p, DeserializationContext ctxt,
        TypeDeserializer typeDeserializer) throws IOException
{
    // If no typeDeserializer is provided, just skip children and return null
    if (typeDeserializer == null) {
        p.skipChildren();
        return null;
    }

    // Use the typeDeserializer to properly handle polymorphic deserialization
    switch (p.getCurrentTokenId()) {
    case JsonTokenId.ID_START_ARRAY:
    case JsonTokenId.ID_START_OBJECT:
    case JsonTokenId.ID_FIELD_NAME:
        return typeDeserializer.deserializeTypedFromAny(p, ctxt);
    default:
        return null;
    }
}
