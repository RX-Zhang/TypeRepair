public final void serialize(JsonGenerator gen, SerializerProvider ctxt) throws IOException
{
    if (_value == null) {
        ctxt.defaultSerializeNull(gen);
    } else if (_value instanceof JsonSerializable) {
        ((JsonSerializable) _value).serialize(gen, ctxt);
    } else {
        // Use asText() to correctly serialize string representation, allowing for custom serialization
        String text = asText();
        gen.writeString(text);
    }
}
