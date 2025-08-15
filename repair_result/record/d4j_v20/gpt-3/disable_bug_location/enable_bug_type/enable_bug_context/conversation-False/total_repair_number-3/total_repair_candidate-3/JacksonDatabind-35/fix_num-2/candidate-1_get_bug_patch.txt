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
                "need JSON Object to contain As.WRAPPER_OBJECT type information for class "+baseTypeName());
    }
    // must get next token, expecting FIELD_NAME with type id
    if (p.nextToken() != JsonToken.FIELD_NAME) {
        throw ctxt.wrongTokenException(p, JsonToken.FIELD_NAME,
                "need JSON String that contains type id (for subtype of "+baseTypeName()+")");
    }
    final String typeId = p.getText();
    JsonDeserializer<Object> deser = _findDeserializer(ctxt, typeId);
    // advance to next token, typically start of actual object/value
    if (p.nextToken() == JsonToken.START_OBJECT && _typeIdVisible) {
        // need to insert type id property into the object
        TokenBuffer tb = new TokenBuffer(null, false);
        tb.writeStartObject(); // recreate START_OBJECT
        tb.writeFieldName(_typePropertyName);
        tb.writeString(typeId);
        // copy existing fields of the object
        // We do not consume the START_OBJECT token here as it is current token,
        // so just chain it after the TokenBuffer parser
        p = JsonParserSequence.createFlattened(tb.asParser(p), p);
        // advance to first token of the merged parser (should be first field name in object)
        p.nextToken();
    }
    
    Object value = deser.deserialize(p, ctxt);
    
    // Expect closing END_OBJECT of the wrapper
    if (p.nextToken() != JsonToken.END_OBJECT) {
        throw ctxt.wrongTokenException(p, JsonToken.END_OBJECT,
                "expected closing END_OBJECT after type information and deserialized value");
    }
    return value;
}
