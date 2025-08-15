private final Object _deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // 02-Aug-2013, tatu: May need to use native type ids
    if (p.canReadTypeId()) {
        Object typeId = p.getTypeId();
        if (typeId != null) {
            return _deserializeWithNativeTypeId(p, ctxt, typeId);
        }
    }
    // first, sanity checks
    if (p.getCurrentToken() != JsonToken.START_OBJECT) {
        throw ctxt.wrongTokenException(p, JsonToken.START_OBJECT,
                "need JSON Object to contain As.WRAPPER_OBJECT type information for class " + baseTypeName());
    }
    // Move to the first FIELD_NAME inside the wrapper object
    if (p.nextToken() != JsonToken.FIELD_NAME) {
        throw ctxt.wrongTokenException(p, JsonToken.FIELD_NAME,
                "need JSON String that contains type id (for subtype of " + baseTypeName() + ")");
    }
    final String typeId = p.getText();
    JsonDeserializer<Object> deser = _findDeserializer(ctxt, typeId);
    
    // Move to the value (START_OBJECT) to be deserialized
    if (p.nextToken() != JsonToken.START_OBJECT && !_typeIdVisible) {
        // If type id not visible, expect object start for value
        throw ctxt.wrongTokenException(p, JsonToken.START_OBJECT,
                "expected START_OBJECT token after type id for subtype of " + baseTypeName());
    }
    
    // If type id visible and current token is START_OBJECT, inject type id field
    if (_typeIdVisible && p.getCurrentToken() == JsonToken.START_OBJECT) {
        TokenBuffer tb = new TokenBuffer(null, false);
        tb.writeStartObject();
        tb.writeFieldName(_typePropertyName);
        tb.writeString(typeId);
        p = JsonParserSequence.createFlattened(tb.asParser(p), p);
        p.nextToken();
    }
    
    Object value = deser.deserialize(p, ctxt);
    // And then need the closing END_OBJECT of the wrapper
    if (p.nextToken() != JsonToken.END_OBJECT) {
        throw ctxt.wrongTokenException(p, JsonToken.END_OBJECT,
                "expected closing END_OBJECT after type information and deserialized value");
    }
    return value;
}
