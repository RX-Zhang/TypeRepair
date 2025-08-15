public final void serialize(JsonGenerator gen, SerializerProvider ctxt) throws IOException
{
    if (_value == null) {
        ctxt.defaultSerializeNull(gen);
    } else if (_value instanceof JsonSerializable) {
        ((JsonSerializable) _value).serialize(gen, ctxt);
    } else {
        Object toSerialize = _value;
        // Use asText() to ensure any context-specific string conversion is used, if necessary
        if (_value instanceof String) {
            toSerialize = _value;
        } else {
            String text = asText();
            if (text != null) {
                toSerialize = text;
            }
        }
        gen.writeObject(toSerialize);
    }
}
