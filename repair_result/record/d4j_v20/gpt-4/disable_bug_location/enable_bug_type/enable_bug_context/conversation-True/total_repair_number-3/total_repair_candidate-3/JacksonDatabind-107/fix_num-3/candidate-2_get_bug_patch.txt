protected final JsonDeserializer<Object> _findDeserializer(DeserializationContext ctxt,
        String typeId) throws IOException
{
    JsonDeserializer<Object> deser = _deserializers.get(typeId);
    if (deser == null) {
        JavaType type = _idResolver.typeFromId(ctxt, typeId);
        if (type == null) {
            deser = _findDefaultImplDeserializer(ctxt);
            if (deser == null) {
                JavaType actual = null;
                try {
                    actual = _handleUnknownTypeId(ctxt, typeId);
                } catch (Exception e) {
                    // In case _handleUnknownTypeId throws, wrap or re-throw appropriately
                    return null;
                }
                if (actual == null) {
                    return null;
                }
                deser = ctxt.findContextualValueDeserializer(actual, _property);
            }
        } else {
            if ((_baseType != null)
                    && _baseType.getClass() == type.getClass()) {
                if (!type.hasGenericTypes()) {
                    type = ctxt.getTypeFactory().constructSpecializedType(_baseType, type.getRawClass());
                }
            }
            deser = ctxt.findContextualValueDeserializer(type, _property);
        }
        if (deser != null) {
            _deserializers.put(typeId, deser);
        }
    }
    return deser;
}
