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

    // Move to next token which should be FIELD_NAME for type id
    if (p.nextToken() != JsonToken.FIELD_NAME) {
        throw ctxt.wrongTokenException(p, JsonToken.FIELD_NAME,
                "need JSON String that contains type id (for subtype of "+baseTypeName()+")");
    }
    final String typeId = p.getText();
    JsonDeserializer<Object> deser = _findDeserializer(ctxt, typeId);

    // Move to the next token after field name (type id)
    if (p.nextToken() != JsonToken.START_OBJECT) {
        throw ctxt.wrongTokenException(p, JsonToken.START_OBJECT, 
                "need JSON Object to contain subtype value for "+baseTypeName());
    }

    // Minor complication: we may need to merge type id in?
    if (_typeIdVisible) {
        TokenBuffer tb = new TokenBuffer(null, false);
        tb.writeStartObject();
        tb.writeFieldName(_typePropertyName);
        tb.writeString(typeId);
        if (p.nextToken() != JsonToken.END_OBJECT) {
            // copy other fields
            do {
                tb.copyCurrentStructure(p);
            } while (p.nextToken() != JsonToken.END_OBJECT);
        }
        tb.writeEndObject();
        p = tb.asParser(p);
        p.nextToken();
    }

    Object value = deser.deserialize(p, ctxt);

    // And then need the closing END_OBJECT for the wrapping type
    if (p.nextToken() != JsonToken.END_OBJECT) {
        throw ctxt.wrongTokenException(p, JsonToken.END_OBJECT,
                "expected closing END_OBJECT after type information and deserialized value");
    }
    return value;
}
