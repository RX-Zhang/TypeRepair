public final void serialize(JsonGenerator gen, SerializerProvider ctxt) throws IOException
{
    if (_value == null) {
        ctxt.defaultSerializeNull(gen);
    } else if (_value instanceof ContextualSerializable) {
        ((ContextualSerializable) _value).serializeWithType(gen, ctxt, ctxt.getConfig().getTypeFactory().constructType(_value.getClass()));
    } else if (_value instanceof JsonSerializable) {
        ((JsonSerializable) _value).serialize(gen, ctxt);
    } else {
        // 25-May-2018, tatu: [databind#1991] do not call via generator but through context;
        //    this to preserve contextual information and attributes
        ctxt.defaultSerializeValue(gen, _value);
    }
}
