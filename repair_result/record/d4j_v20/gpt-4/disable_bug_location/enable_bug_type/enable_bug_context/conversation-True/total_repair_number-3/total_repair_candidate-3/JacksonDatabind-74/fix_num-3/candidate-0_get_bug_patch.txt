protected Object _deserializeTypedUsingDefaultImpl(JsonParser p, DeserializationContext ctxt,
        TokenBuffer tb) throws IOException
{
    // As per [JACKSON-614], may have default implementation to use
    JsonDeserializer<Object> deser = _findDefaultImplDeserializer(ctxt);
    if (deser != null) {
        if (tb != null) {
            tb.writeEndObject();
            p = tb.asParser(p);
            // must move to point to the first token:
            p.nextToken();
        }
        return deser.deserialize(p, ctxt);
    }
    // or, perhaps we just bumped into a "natural" value (boolean/int/double/String)?
    Object result = TypeDeserializer.deserializeIfNatural(p, ctxt, _baseType);
    if (result != null) {
        return result;
    }
    // or, something for which "as-property" won't work, changed into "wrapper-array" type:
    if (p.getCurrentToken() == JsonToken.START_ARRAY) {
        return super.deserializeTypedFromAny(p, ctxt);
    }
    // Changed here: only report exception if current token is not FIELD_NAME to avoid false error
    if (p.getCurrentToken() != JsonToken.FIELD_NAME) {
        ctxt.reportWrongTokenException(p, JsonToken.FIELD_NAME,
                "missing property '"+_typePropertyName+"' that is to contain type id  (for class "+baseTypeName()+")");
    }
    return null;
}
