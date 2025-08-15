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
            return _createEnumKeyDeserializer(ctxt, type);
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

private KeyDeserializer _createEnumKeyDeserializer(DeserializationContext ctxt, JavaType type) throws JsonMappingException {
    final DeserializationConfig config = ctxt.getConfig();
    final Class<?> enumClass = type.getRawClass();

    // find jsonValue method if any (to honor @JsonValue on enum methods)
    AnnotatedMethod jsonValueMethod = _findJsonValueFor(config, type);
    EnumResolver enumRes = constructEnumResolver(enumClass, config, jsonValueMethod);

    return new StdKeyDeserializer.EnumKD(enumRes, jsonValueMethod);
}
