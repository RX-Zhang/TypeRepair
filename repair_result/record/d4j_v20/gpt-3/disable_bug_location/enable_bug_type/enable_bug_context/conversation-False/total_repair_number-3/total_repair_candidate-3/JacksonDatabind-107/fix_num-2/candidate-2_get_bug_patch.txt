protected final JsonDeserializer<Object> _findDeserializer(DeserializationContext ctxt,
        String typeId) throws IOException
{
    JsonDeserializer<Object> deser = _deserializers.get(typeId);
    if (deser == null) {
        JavaType type = _idResolver.typeFromId(ctxt, typeId);
        if (type == null) {
            deser = _findDefaultImplDeserializer(ctxt);
            if (deser == null) {
                JavaType actual = _handleUnknownTypeId(ctxt, typeId);
                if (actual == null) {
                    return null;
                }
                // Ensure that the type is not null before using it
                try {
                    deser = ctxt.findContextualValueDeserializer(actual, _property);
                } catch (Exception e) {
                    // Handle potential exceptions from deserializer lookup
                    throw new IOException("Failed to find contextual value deserializer for type: " + actual, e);
                }
            }
        } else {
            if ((_baseType != null)
                    && _baseType.getClass() == type.getClass()) {
                if (!type.hasGenericTypes()) {
                    type = ctxt.getTypeFactory().constructSpecializedType(_baseType, type.getRawClass());
                }
            }
            // Ensure that the type is not null before using it
            try {
                deser = ctxt.findContextualValueDeserializer(type, _property);
            } catch (Exception e) {
                // Handle potential exceptions from deserializer lookup
                throw new IOException("Failed to find contextual value deserializer for type: " + type, e);
            }
        }
        _deserializers.put(typeId, deser);
    }
    return deser;
}
