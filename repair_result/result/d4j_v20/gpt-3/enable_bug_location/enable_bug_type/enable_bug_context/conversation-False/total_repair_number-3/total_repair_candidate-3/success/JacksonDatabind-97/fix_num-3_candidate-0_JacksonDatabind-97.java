public final void serialize(JsonGenerator gen, SerializerProvider ctxt) throws IOException
{
    if (_value == null) {
        ctxt.defaultSerializeNull(gen);
    } else if (_value instanceof JsonSerializable) {
        JsonSerializable ser = (JsonSerializable) _value;
        ser.serialize(gen, ctxt);
    } else {
        // 25-May-2018, tatu: [databind#1991] do not call via generator but through context;
        //    this to preserve contextual information and allow contextual serializers to apply
        ctxt.defaultSerializeValue(_value, gen);
    }
}
