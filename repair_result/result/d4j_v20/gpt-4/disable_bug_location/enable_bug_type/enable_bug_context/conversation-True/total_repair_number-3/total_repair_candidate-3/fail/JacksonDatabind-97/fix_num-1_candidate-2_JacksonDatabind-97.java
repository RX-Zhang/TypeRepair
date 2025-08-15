public final void serialize(JsonGenerator gen, SerializerProvider ctxt) throws IOException
{
    if (_value == null) {
        ctxt.defaultSerializeNull(gen);
    } else if (_value instanceof JsonSerializable) {
        ((JsonSerializable) _value).serialize(gen, ctxt);
    } else {
        Object pojo = getPojo();
        if (pojo instanceof String) {
            gen.writeString((String) pojo);
        } else {
            gen.writeObject(pojo);
        }
    }
}
