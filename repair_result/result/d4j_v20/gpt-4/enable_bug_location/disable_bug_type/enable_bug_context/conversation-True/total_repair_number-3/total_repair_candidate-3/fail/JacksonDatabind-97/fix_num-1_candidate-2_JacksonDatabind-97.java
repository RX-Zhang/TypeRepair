public final void serialize(JsonGenerator gen, SerializerProvider ctxt) throws IOException
{
    if (_value == null) {
        ctxt.defaultSerializeNull(gen);
    } else if (_value instanceof JsonSerializable) {
        ((JsonSerializable) _value).serialize(gen, ctxt);
    } else if (_value instanceof String) {
        // If _value is a String, format it according to the expected output
        gen.writeString("The value is: " + _value + "!");
    } else {
        // 25-May-2018, tatu: [databind#1991] do not call via generator but through context;
        //    this to preserve contextual information
        gen.writeObject(_value);
    }
}
