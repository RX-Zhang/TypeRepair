public final void serialize(JsonGenerator gen, SerializerProvider ctxt) throws IOException
{
    if (_value == null) {
        ctxt.defaultSerializeNull(gen);
    } else if (_value instanceof JsonSerializable) {
        ((JsonSerializable) _value).serialize(gen, ctxt);
    } else if (_value instanceof String) {
        gen.writeString("The value is: " + _value.toString() + "!");
    } else {
        gen.writeObject(_value);
    }
}
