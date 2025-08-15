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
    // It's possible the field name is not yet read, so move to it if needed
    if (p.nextToken() != JsonToken.FIELD_NAME) {
        throw ctxt.wrongTokenException(p, JsonToken.FIELD_NAME,
                "need JSON String that contains type id (for subtype of "+baseTypeName()+")");
    }
    final String typeId = p.getText();
    JsonDeserializer<Object> deser = _findDeserializer(ctxt, typeId);
    if (p.nextToken() != JsonToken.START_OBJECT) {
        throw ctxt.wrongTokenException(p, JsonToken.START_OBJECT,
                "need JSON Object to contain value for subtype of "+baseTypeName());
    }

    // Minor complication: we may need to merge type id in?
    if (_typeIdVisible) {
        TokenBuffer tb = new TokenBuffer(null, false);
        tb.writeStartObject();
        tb.writeFieldName(_typePropertyName);
        tb.writeString(typeId);
        // copy all current object fields from parser into token buffer
        while (p.nextToken() != JsonToken.END_OBJECT) {
            tb.copyCurrentStructure(p);
        }
        tb.writeEndObject();
        JsonParser seq = tb.asParser(p);
        seq.nextToken();
        Object value = deser.deserialize(seq, ctxt);
        if (seq.nextToken() != null) {
            throw ctxt.wrongTokenException(p, JsonToken.END_OBJECT,
                "expected closing END_OBJECT after type information and deserialized value");
        }
        return value;
    } else {
        Object value = deser.deserialize(p, ctxt);
        if (p.nextToken() != JsonToken.END_OBJECT) {
            throw ctxt.wrongTokenException(p, JsonToken.END_OBJECT,
                    "expected closing END_OBJECT after type information and deserialized value");
        }
        return value;
    }
}
