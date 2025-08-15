public final void serialize(JsonGenerator gen, SerializerProvider ctxt) throws IOException
{
    if (_value == null) {
        ctxt.defaultSerializeNull(gen);
    } else if (_value instanceof JsonSerializable) {
        ((JsonSerializable) _value).serialize(gen, ctxt);
    } else {
        // Use the asText() method to get the properly converted String value,
        // so that custom serialization logic depending on context attributes is honored.
        gen.writeString(asText());
    }
}
