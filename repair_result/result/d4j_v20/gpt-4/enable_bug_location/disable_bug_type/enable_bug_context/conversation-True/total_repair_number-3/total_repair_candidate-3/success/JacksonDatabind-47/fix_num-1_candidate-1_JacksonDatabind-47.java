public JavaType refineSerializationType(final MapperConfig<?> config,
        final Annotated a, final JavaType baseType) throws JsonMappingException
{
    JavaType type = baseType;
    final TypeFactory tf = config.getTypeFactory();

    // Ok: start by refining the main type itself; common to all types
    Class<?> serClass = findSerializationType(a);
    if (serClass != null) {
        if (type.hasRawClass(serClass)) {
            type = type.withStaticTyping();
        } else {
            try {
                Class<?> currRaw = type.getRawClass();
                if (currRaw.isAssignableFrom(serClass)) {
                    type = tf.constructSpecializedType(type, serClass);
                } else if (serClass.isAssignableFrom(currRaw)) {
                    type = tf.constructGeneralizedType(type, serClass);
                } else {
                    throw new JsonMappingException(null,
                            String.format("Can not refine serialization type %s into %s; types not related",
                                    type, serClass.getName()));
                }
            } catch (IllegalArgumentException iae) {
                throw new JsonMappingException(null,
                        String.format("Failed to widen type %s with annotation (value %s), from '%s': %s",
                                type, serClass.getName(), a.getName(), iae.getMessage()),
                                iae);
            }
        }
    }
    // Then further processing for container types

    // First, key type (for Maps, Map-like types):
    if (type.isMapLikeType()) {
        JavaType keyType = type.getKeyType();
        Class<?> keyClass = findSerializationKeyType(a, keyType);
        if (keyClass != null) {
            if (keyType.hasRawClass(keyClass)) {
                keyType = keyType.withStaticTyping();
            } else {
                Class<?> currRaw = keyType.getRawClass();
                try {
                    if (currRaw.isAssignableFrom(keyClass)) {
                        keyType = tf.constructSpecializedType(keyType, keyClass);
                    } else if (keyClass.isAssignableFrom(currRaw)) {
                        keyType = tf.constructGeneralizedType(keyType, keyClass);
                    } else {
                        throw new JsonMappingException(null,
                                String.format("Can not refine serialization key type %s into %s; types not related",
                                        keyType, keyClass.getName()));
                    }
                } catch (IllegalArgumentException iae) {
                    throw new JsonMappingException(null,
                            String.format("Failed to widen key type of %s with concrete-type annotation (value %s), from '%s': %s",
                                    type, keyClass.getName(), a.getName(), iae.getMessage()),
                                    iae);
                }
            }
            type = ((MapLikeType) type).withKeyType(keyType);
        }
    }

    JavaType contentType = type.getContentType();
    if (contentType != null) { // collection[like], map[like], array, reference
        Class<?> contentClass = findSerializationContentType(a, contentType);
        if (contentClass != null) {
            if (contentType.hasRawClass(contentClass)) {
                contentType = contentType.withStaticTyping();
            } else {
                Class<?> currRaw = contentType.getRawClass();
                try {
                    if (currRaw.isAssignableFrom(contentClass)) {
                        contentType = tf.constructSpecializedType(contentType, contentClass);
                    } else if (contentClass.isAssignableFrom(currRaw)) {
                        contentType = tf.constructGeneralizedType(contentType, contentClass);
                    } else {
                        throw new JsonMappingException(null,
                                String.format("Can not refine serialization content type %s into %s; types not related",
                                        contentType, contentClass.getName()));
                    }
                } catch (IllegalArgumentException iae) {
                    throw new JsonMappingException(null,
                            String.format("Internal error: failed to refine value type of %s with concrete-type annotation (value %s), from '%s': %s",
                                    type, contentClass.getName(), a.getName(), iae.getMessage()),
                                    iae);
                }
            }
            type = type.withContentType(contentType);
        }
    }
    return type;
}
