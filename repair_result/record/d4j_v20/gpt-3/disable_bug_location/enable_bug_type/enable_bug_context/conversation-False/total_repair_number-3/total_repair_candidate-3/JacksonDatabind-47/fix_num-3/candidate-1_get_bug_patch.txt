public JavaType refineSerializationType(final MapperConfig<?> config,
        final Annotated a, final JavaType baseType) throws JsonMappingException
{
    JavaType type = baseType;
    final TypeFactory tf = config.getTypeFactory();

    // Ok: start by refining the main type itself; common to all types
    Class<?> serClass = findSerializationType(a);
    if (serClass != null) {
        if (serClass.isAssignableFrom(type.getRawClass())) {
            // 30-Nov-2015, tatu: As per [databind#1023], need to allow forcing of
            //    static typing this way
            type = type.withStaticTyping();
        } else if (type.getRawClass().isAssignableFrom(serClass)) {
            try {
                type = tf.constructSpecializedType(type, serClass);
            } catch (IllegalArgumentException iae) {
                throw new JsonMappingException(null,
                        String.format("Failed to widen type %s with annotation (value %s), from '%s': %s",
                                type, serClass.getName(), a.getName(), iae.getMessage()),
                                iae);
            }
        } else {
            throw new JsonMappingException(null,
                    String.format("Failed to widen type %s with annotation (value %s), from '%s': Class %s not a super-type of [%s]",
                            type, serClass.getName(), a.getName(), serClass.getName(), type.getRawClass().getName()));
        }
    }
    // Then further processing for container types

    // First, key type (for Maps, Map-like types):
    if (type.isMapLikeType()) {
        JavaType keyType = type.getKeyType();
        Class<?> keyClass = findSerializationKeyType(a, keyType);
        if (keyClass != null) {
            if (keyClass.isAssignableFrom(keyType.getRawClass())) {
                keyType = keyType.withStaticTyping();
            } else if (keyType.getRawClass().isAssignableFrom(keyClass)) {
                try {
                    keyType = tf.constructSpecializedType(keyType, keyClass);
                } catch (IllegalArgumentException iae) {
                    throw new JsonMappingException(null,
                            String.format("Failed to widen key type of %s with concrete-type annotation (value %s), from '%s': %s",
                                    type, keyClass.getName(), a.getName(), iae.getMessage()),
                                    iae);
                }
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
                contentType = contentType.withStaticTyping();
            } else if (contentType.getRawClass().isAssignableFrom(contentClass)) {
                try {
                    contentType = tf.constructSpecializedType(contentType, contentClass);
                } catch (IllegalArgumentException iae) {
                    throw new JsonMappingException(null,
                            String.format("Internal error: failed to refine value type of %s with concrete-type annotation (value %s), from '%s': %s",
                                    type, contentClass.getName(), a.getName(), iae.getMessage()),
                                    iae);
                }
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
