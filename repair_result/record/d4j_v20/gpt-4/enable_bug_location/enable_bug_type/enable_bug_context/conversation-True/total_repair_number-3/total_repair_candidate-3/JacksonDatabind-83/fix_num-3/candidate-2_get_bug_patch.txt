public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    String text = p.getValueAsString();
    if (text != null) { // has String representation
        text = text.trim();
        if (text.length() == 0) {
            // Empty string case: usually returns null
            return _deserializeFromEmptyString();
        }
        Exception cause = null;
        try {
            T result = _deserialize(text, ctxt);
            // Allow null as valid return value
            return result;
        } catch (IllegalArgumentException | MalformedURLException ex) {
            cause = ex;
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
    if (t == JsonToken.START_ARRAY) {
        return _deserializeFromArray(p, ctxt);
    }
    if (t == JsonToken.VALUE_EMBEDDED_OBJECT) {
        Object ob = p.getEmbeddedObject();
        if (ob == null) {
            return null;
        }
        if (_valueClass.isAssignableFrom(ob.getClass())) {
            return (T) ob;
        }
        return _deserializeEmbedded(ob, ctxt);
    }
    return (T) ctxt.handleUnexpectedToken(_valueClass, p);
}
