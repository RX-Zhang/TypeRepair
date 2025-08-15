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
            // use EnumResolver to handle case insensitivity and mappings properly
            Class<?> enumClass = type.getRawClass();
            BeanDescription beanDesc = config.introspectForDeserialization(enumClass);
            AnnotatedMethod jsonValueMethod = _findJsonValueFor(config, type);
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

// Adjusted _createEnumKeyDeserializer to accept EnumResolver
private KeyDeserializer _createEnumKeyDeserializer(DeserializationContext ctxt, JavaType type, EnumResolver enumRes) {
    return new StdKeyDeserializer.EnumKD(type.getRawClass(), enumRes);
}

// Existing method fallback for _findJsonValueFor as per context
protected AnnotatedMethod _findJsonValueFor(DeserializationConfig config, JavaType enumType) {
    // This method should find @JsonValue annotated method on enum, placeholder if needed
    BeanDescription beanDesc = config.introspect(enumType);
    return config.getAnnotationIntrospector().findJsonValueMethod(beanDesc.getClassInfo());
}
