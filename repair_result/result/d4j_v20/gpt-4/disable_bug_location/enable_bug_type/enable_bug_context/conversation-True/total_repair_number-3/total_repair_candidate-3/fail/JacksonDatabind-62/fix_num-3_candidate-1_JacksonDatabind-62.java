public CollectionDeserializer createContextual(DeserializationContext ctxt,
        BeanProperty property) throws JsonMappingException
{
    JsonDeserializer<Object> delegateDeser = null;
    if (_valueInstantiator != null) {
        if (_valueInstantiator.canCreateUsingDelegate()) {
            JavaType delegateType = _valueInstantiator.getDelegateType(ctxt.getConfig());
            if (delegateType == null) {
                throw new IllegalArgumentException("Invalid delegate-creator definition for "+_collectionType
                        +": value instantiator ("+_valueInstantiator.getClass().getName()
                        +") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
            }
            delegateDeser = findDeserializer(ctxt, delegateType, property);
            if (delegateDeser == null) {
                throw new JsonMappingException(ctxt.getParser(),
                    "Failed to find delegate deserializer for type " + delegateType);
            }
        }
    }
    Boolean unwrapSingle = findFormatFeature(ctxt, property, Collection.class,
            JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    JsonDeserializer<?> valueDeser = _valueDeserializer;
    
    valueDeser = findConvertingContentDeserializer(ctxt, property, valueDeser);
    final JavaType vt = _collectionType.getContentType();
    if (valueDeser == null) {
        valueDeser = ctxt.findContextualValueDeserializer(vt, property);
    } else {
        valueDeser = ctxt.handleSecondaryContextualization(valueDeser, property, vt);
    }

    TypeDeserializer valueTypeDeser = _valueTypeDeserializer;
    if (valueTypeDeser != null) {
        valueTypeDeser = valueTypeDeser.forProperty(property);
    }

    try {
        return withResolved(delegateDeser, valueDeser, valueTypeDeser, unwrapSingle);
    } catch (IllegalStateException e) {
        // Catching the case where no default constructor is available for certain collection types
        throw JsonMappingException.from(ctxt,
                "Cannot create CollectionDeserializer for type "+_collectionType+": " + e.getMessage(), e);
    }
}
