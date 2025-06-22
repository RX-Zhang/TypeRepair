public JavaType refineSerializationType(final MapperConfig<?> config,
        final Annotated a, final JavaType baseType) throws JsonMappingException
{
    JavaType type = baseType;
    final TypeFactory tf = config.getTypeFactory();
    
    // Ok: start by refining the main type itself; common to all types
    Class<?> serClass = findSerializationType(a);
    if (serClass != null) {
        if (serClass != type.getRawClass()) {
            try {
                // Need to specialize or generalize depending on relationship
                Class<?> currRaw = type.getRawClass();
                if (serClass.isAssignableFrom(currRaw)) {
                    type = tf.constructGeneralizedType(type, serClass);
                } else if (currRaw.isAssignableFrom(serClass)) {
                    type = tf.constructSpecializedType(type, serClass);
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
        } else {
            // same raw class; can force static typing
            type = type.withStaticTyping();
        }
    }
    // Then further processing for container types

    // First, key type (for Maps, Map-like types):
    if (type.isMapLikeType()) {
        JavaType keyType = type.getKeyType();
        Class<?> keyClass = findSerializationKeyType(a, keyType);
        if (keyClass != null) {
            if (keyClass != keyType.getRawClass()) {
                Class<?> currRaw = keyType.getRawClass();
                try {
                    if (keyClass.isAssignableFrom(currRaw)) { // generalize
                        keyType = tf.constructGeneralizedType(keyType, keyClass);
                    } else if (currRaw.isAssignableFrom(keyClass)) { // specialize
                        keyType = tf.constructSpecializedType(keyType, keyClass);
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
            } else {
                keyType = keyType.withStaticTyping();
            }
            type = ((MapLikeType) type).withKeyType(keyType);
        }
    }

    JavaType contentType = type.getContentType();
    if (contentType != null) { // collection[like], map[like], array, reference
       Class<?> contentClass = findSerializationContentType(a, contentType);
       if (contentClass != null) {
           if (contentClass != contentType.getRawClass()) {
               Class<?> currRaw = contentType.getRawClass();
               try {
                   if (contentClass.isAssignableFrom(currRaw)) { // generalize
                       contentType = tf.constructGeneralizedType(contentType, contentClass);
                   } else if (currRaw.isAssignableFrom(contentClass)) { // specialize
                       contentType = tf.constructSpecializedType(contentType, contentClass);
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
           } else {
               contentType = contentType.withStaticTyping();
           }
           type = type.withContentType(contentType);
       }
    }
    return type;
}
