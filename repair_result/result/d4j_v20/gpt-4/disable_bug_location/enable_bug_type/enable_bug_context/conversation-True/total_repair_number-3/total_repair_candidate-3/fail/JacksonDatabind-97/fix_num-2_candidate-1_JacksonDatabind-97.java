public final void serialize(JsonGenerator gen, SerializerProvider ctxt) throws IOException
{
    if (_value == null) {
        ctxt.defaultSerializeNull(gen);
    } else if (_value instanceof JsonSerializable) {
        ((JsonSerializable) _value).serialize(gen, ctxt);
    } else {
        Object toSerialize = _value;
        if (toSerialize instanceof POJONode) {
            toSerialize = ((POJONode) toSerialize).getPojo();
        }
        gen.writeObject(toSerialize);
    }
}
