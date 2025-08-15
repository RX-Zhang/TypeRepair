public JavaType refineSerializationType(final MapperConfig<?> config,
        final Annotated a, final JavaType baseType) throws JsonMappingException
{
    JavaType type = baseType;
    final TypeFactory tf = config.getTypeFactory();
    
    // 10-Oct-2015, tatu: For 2.7, we'll need to delegate back to
    //    now-deprecated secondary methods; this because while
    //    direct sub-class not yet retrofitted may only override
    //    those methods. With 2.8 or later we may consider removal
    //    of these methods

    
    // Ok: start by refining the main type itself; common to all types
    Class<?> serClass = findSerializationType(a);
    if (serClass != null) {
        if (serClass.isAssignableFrom(type.getRawClass())) {
            // 30-Nov-2015, tatu: As per [databind#1023], need to allow forcing of
            //    static typing this way
            type = type.withStaticTyping();
        } else {
            try {
                // 11-Oct-2015, tatu: For deser, we call `TypeFactory.constructSpecializedType()`,
                //   may be needed here too in future?
                    type = tf.constructGeneralizedType(type, serClass);
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
                    // 19-May-2016, tatu: As per [databind#1231], [databind#1178] may need to actually
                    //   specialize (narrow) type sometimes, even if more commonly opposite
                    //   is needed.
                    if (currRaw.isAssignableFrom(keyClass)) { // common case
                        keyType = tf.constructSpecializedType(keyType, keyClass);
                    } else if (keyClass.isAssignableFrom(currRaw)) { // generalization, ok as well
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
        // And then value types for all containers:
       Class<?> contentClass = findSerializationContentType(a, contentType);
       if (contentClass != null) {
           if (contentType.hasRawClass(contentClass)) {
               contentType = contentType.withStaticTyping();
           } else {
               // 03-Apr-2016, tatu: As per [databind#1178], may need to actually
               //   specialize (narrow) type sometimes, even if more commonly opposite
               //   is needed.
               Class<?> currRaw = contentType.getRawClass();
               try {
                   if (currRaw.isAssignableFrom(contentClass)) { // common case
                       contentType = tf.constructSpecializedType(contentType, contentClass);
                   } else if (contentClass.isAssignableFrom(currRaw)) { // generalization, ok as well
                       contentType = tf.constructGeneralizedType(contentType, contentClass);
                   } else {
                       throw new JsonMappingException(null,
                               String.format("Can not refine serialization content type %s into %s; types not related",
                                       contentType, contentClass.getName()));
                   }
               } catch (IllegalArgumentException iae) { // shouldn't really happen
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
