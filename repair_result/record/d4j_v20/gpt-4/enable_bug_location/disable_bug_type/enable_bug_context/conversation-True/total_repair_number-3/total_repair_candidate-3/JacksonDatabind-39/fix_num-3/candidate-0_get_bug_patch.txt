public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // For token types that are structured, skip using deserializeWithType
    if (p.hasToken(JsonToken.START_OBJECT) || p.hasToken(JsonToken.START_ARRAY)) {
        p.skipChildren();
    } else if (p.hasToken(JsonToken.FIELD_NAME)) {
        // deserializer is called on a field name token; move to the next token and skip the children of that token.
        p.nextToken();
        p.skipChildren();
    } else {
        // For other token types, just skip without children
        p.skipChildren();
    }
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
