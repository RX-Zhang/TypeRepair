public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // 22-Sep-2012, tatu: For 2.1, use this new method, may force coercion:
    String text = p.getValueAsString();
    if (text != null) { // has String representation
        text = text.trim();
        if (text.length() == 0) {
            // 04-Feb-2013, tatu: Usually should become null; but not always
            return _deserializeFromEmptyString();
        }
        Exception cause = null;
        try {
            T value = _deserialize(text, ctxt);
            // Accept null as valid value
            if (value != null) {
                return value;
            }
        } catch (IllegalArgumentException iae) {
            cause = iae;
        } catch (MalformedURLException me) {
            cause = me;
        }
        // If we have a null _valueClass (like for WeirdStringHandler with null enum),
        // or if deserialization failed, return null instead of throwing exception
        if (_valueClass == null) {
            return null;
        }
        String msg = "not a valid textual representation";
        if (cause != null) {
            String m2 = cause.getMessage();
            if (m2 != null) {
                msg = msg + ", problem: "+m2;
            }
        }
        JsonMappingException e = ctxt.weirdStringException(text, _valueClass, msg);
        if (cause != null) {
            e.initCause(cause);
        }
        throw e;
    }
    JsonToken t = p.getCurrentToken();
    // [databind#381]
    if (t == JsonToken.START_ARRAY) {
        return _deserializeFromArray(p, ctxt);
    }
    if (t == JsonToken.VALUE_EMBEDDED_OBJECT) {
        // Trivial cases; null to null, instance of type itself returned as is
        Object ob = p.getEmbeddedObject();
        if (ob == null) {
            return null;
        }
        if (_valueClass != null && _valueClass.isAssignableFrom(ob.getClass())) {
            return (T) ob;
        }
        return _deserializeEmbedded(ob, ctxt);
    }
    return (T) ctxt.handleUnexpectedToken(_valueClass, p);
}
