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
    if (deser == null) {
        if (type.isEnumType()) {
            // Use the internal method to create Enum key deserializer correctly
            deser = _createEnumKeyDeserializer(ctxt, type);
        } else {
            deser = StdKeyDeserializers.findStringBasedKeyDeserializer(config, type);
        }
    }
    // then post-processing with modifiers
    if (deser != null) {
        if (_factoryConfig.hasDeserializerModifiers()) {
            for (BeanDeserializerModifier mod : _factoryConfig.deserializerModifiers()) {
                KeyDeserializer modified = mod.modifyKeyDeserializer(config, type, deser);
                if (modified != null) {
                    deser = modified;
                }
            }
        }
    }
    return deser;
}
