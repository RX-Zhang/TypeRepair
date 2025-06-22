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
    JsonToken curr = p.getCurrentToken();
    // or, something for which "as-property" won't work, changed into "wrapper-array" type:
    if (curr == JsonToken.START_ARRAY) {
        return super.deserializeTypedFromAny(p, ctxt);
    }
    // Added support for AcceptEmptyStringAsNullObject feature:
    // if empty string is encountered and feature enabled, consider it null
    if (curr == JsonToken.VALUE_STRING && ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
        String text = p.getText();
        if (text.length() == 0) {
            return null;
        }
    }
    ctxt.reportWrongTokenException(p, JsonToken.FIELD_NAME,
            "missing property '"+_typePropertyName+"' that is to contain type id  (for class "+baseTypeName()+")");
    return null;
}
