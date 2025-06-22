public KeyDeserializer createKeyDeserializer(DeserializationContext ctxt,
        JavaType type)
    throws JsonMappingException
{
    final DeserializationConfig config = ctxt.getConfig();
    KeyDeserializer deser = null;
    if (_factoryConfig.hasKeyDeserializers()) {
        BeanDescription beanDesc = config.introspectClassAnnotations(type.getRawClass());
        for (KeyDeserializers d  : _factoryConfig.keyDeserializers()) {
            deser = d.findKeyDeserializer(type, config, beanDesc);
            if (deser != null) {
                break;
            }
        }
    }
    // the only non-standard thing is this:
    if (deser == null) {
        if (type.isEnumType()) {
            // Use EnumResolver constructed properly to support case-insensitive or custom enum deserialization
            Class<?> enumClass = type.getRawClass();
            AnnotatedMethod jsonValueMethod = null;
            BeanDescription beanDesc = config.introspectClassAnnotations(enumClass);
            // Try to locate @JsonValue annotated method
            jsonValueMethod = _findJsonValueFor(config, type);
            EnumResolver enumRes = constructEnumResolver(enumClass, config, jsonValueMethod);
            return _createEnumKeyDeserializer(ctxt, type, enumRes);
        }
        deser = StdKeyDeserializers.findStringBasedKeyDeserializer(config, type);
    }
    // and then post-processing
    if (deser != null) {
        if (_factoryConfig.hasDeserializerModifiers()) {
            for (BeanDeserializerModifier mod : _factoryConfig.deserializerModifiers()) {
                deser = mod.modifyKeyDeserializer(config, type, deser);
            }
        }
    }
    return deser;
}

// Modified _createEnumKeyDeserializer to take EnumResolver as input
private KeyDeserializer _createEnumKeyDeserializer(DeserializationContext ctxt, JavaType type, EnumResolver enumRes)
        throws JsonMappingException
{
    if (enumRes == null) {
        return null;
    }
    return new StdKeyDeserializer.EnumKD(enumRes, null);
}

// Existing helper method, adapted signature
private AnnotatedMethod _findJsonValueFor(DeserializationConfig config, JavaType enumType) {
    Class<?> enumClass = enumType.getRawClass();
    BeanDescription beanDesc = config.introspectClassAnnotations(enumClass);
    return _findJsonValueFor(config, enumType);
}
