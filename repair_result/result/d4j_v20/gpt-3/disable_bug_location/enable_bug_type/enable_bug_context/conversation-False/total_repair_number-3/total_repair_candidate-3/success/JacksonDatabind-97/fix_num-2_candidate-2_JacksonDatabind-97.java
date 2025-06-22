public final void serialize(JsonGenerator gen, SerializerProvider ctxt) throws IOException
{
    if (_value == null) {
        ctxt.defaultSerializeNull(gen);
    } else {
        // Use the context to serialize the value; it will handle JsonSerializable as well
        ctxt.defaultSerializeValue(_value, gen);
    }
}
