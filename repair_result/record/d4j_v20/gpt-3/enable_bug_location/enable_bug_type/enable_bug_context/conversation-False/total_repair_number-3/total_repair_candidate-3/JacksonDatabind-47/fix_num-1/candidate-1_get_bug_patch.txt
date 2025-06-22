public JavaType refineSerializationType(final MapperConfig<?> config,
        final Annotated a, final JavaType baseType) throws JsonMappingException
{
    JavaType type = baseType;
    final TypeFactory tf = config.getTypeFactory();
    
    // Ok: start by refining the main type itself; common to all types
    Class<?> serClass = findSerializationType(a);
    if (serClass != null) {
        if (serClass.isAssignableFrom(type.getRawClass())) {
            // If the annotated class is a super-type of current type,
            // we can generalize (widen) the type
            type = tf.constructGeneralizedType(type, serClass);
        } else if (type.hasRawClass(serClass)) {
            // Same raw class: just force static typing
            type = type.withStaticTyping();
        } else if (type.getRawClass().isAssignableFrom(serClass)) {
            // If annotated class is subtype/specialization, specialize it
            type = tf.constructSpecializedType(type, serClass);
        } else {
            throw new JsonMappingException(null,
                    String.format("Failed to widen type %s with annotation (value %s), from '%s': Class %s not a super-type or sub-type of %s",
                            type, serClass.getName(), a.getName(), serClass.getName(), type.getRawClass().getName()));
        }
    }
    // Then further processing for container types

    // First, key type (for Maps, Map-like types):
    if (type.isMapLikeType()) {
        JavaType keyType = type.getKeyType();
        Class<?> keyClass = findSerializationKeyType(a, keyType);
        if (keyClass != null) {
            if (keyClass.isAssignableFrom(keyType.getRawClass())) { // generalize
                keyType = tf.constructGeneralizedType(keyType, keyClass);
            } else if (keyType.hasRawClass(keyClass)) { // exact
                keyType = keyType.withStaticTyping();
            } else if (keyType.getRawClass().isAssignableFrom(keyClass)) { // specialize
                keyType = tf.constructSpecializedType(keyType, keyClass);
            } else {
                throw new JsonMappingException(null,
                        String.format("Can not refine serialization key type %s into %s; types not related",
                                keyType, keyClass.getName()));
            }
            type = ((MapLikeType) type).withKeyType(keyType);
        }
    }

    JavaType contentType = type.getContentType();
    if (contentType != null) { // collection[like], map[like], array, reference
        // And then value types for all containers:
       Class<?> contentClass = findSerializationContentType(a, contentType);
       if (contentClass != null) {
           if (contentClass.isAssignableFrom(contentType.getRawClass())) {
               contentType = tf.constructGeneralizedType(contentType, contentClass);
           } else if (contentType.hasRawClass(contentClass)) {
               contentType = contentType.withStaticTyping();
           } else if (contentType.getRawClass().isAssignableFrom(contentClass)) {
               contentType = tf.constructSpecializedType(contentType, contentClass);
           } else {
               throw new JsonMappingException(null,
                       String.format("Can not refine serialization content type %s into %s; types not related",
                               contentType, contentClass.getName()));
           }
           type = type.withContentType(contentType);
       }
    }
    return type;
}
