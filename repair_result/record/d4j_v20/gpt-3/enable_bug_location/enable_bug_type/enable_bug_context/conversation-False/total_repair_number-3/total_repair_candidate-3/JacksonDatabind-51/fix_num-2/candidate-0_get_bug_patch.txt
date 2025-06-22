protected final JsonDeserializer<Object> _findDeserializer(DeserializationContext ctxt,
        String typeId) throws IOException
{
    JsonDeserializer<Object> deser = _deserializers.get(typeId);
    if (deser == null) {
        JavaType type = _idResolver.typeFromId(ctxt, typeId);
        if (type == null) {
            deser = _findDefaultImplDeserializer(ctxt);
            if (deser == null) {
                JavaType actual = _handleUnknownTypeId(ctxt, typeId, _idResolver, _baseType);
                if (actual == null) {
                    return null;
                }
                deser = ctxt.findContextualValueDeserializer(actual, _property);
            }
        } else {
            if ((_baseType != null) && _baseType.getClass() == type.getClass()) {
                type = ctxt.getTypeFactory().constructSpecializedType(_baseType, type.getRawClass());
            }
            // Verify and convert variable types before deserialization
            if (!type.getRawClass().isAssignableFrom(Object.class)) {
                throw new TypeError("Incompatible type for deserialization: " + typeId);
            }
            deser = ctxt.findContextualValueDeserializer(type, _property);
        }
        _deserializers.put(typeId, deser);
    }
    return deser;
}
