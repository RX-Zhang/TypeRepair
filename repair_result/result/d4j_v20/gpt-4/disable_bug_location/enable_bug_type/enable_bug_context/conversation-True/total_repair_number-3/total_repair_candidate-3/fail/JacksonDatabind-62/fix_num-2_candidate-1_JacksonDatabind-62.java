public CollectionDeserializer createContextual(DeserializationContext ctxt,
        BeanProperty property) throws JsonMappingException
{
    // May need to resolve types for delegate-based creators:
    JsonDeserializer<Object> delegateDeser = _delegateDeserializer;
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
                // Defensive check: If no deserializer found for delegate, fallback or error
                throw new JsonMappingException(ctxt.getParser(),
                    "Could not find delegate deserializer for type " + delegateType);
            }
        }
    }
    // Determine unwrapSingle feature flag (may be null if not set)
    Boolean unwrapSingle = findFormatFeature(ctxt, property, Collection.class,
            JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

    // Resolve and contextualize value deserializer
    JsonDeserializer<?> valueDeser = _valueDeserializer;
    valueDeser = findConvertingContentDeserializer(ctxt, property, valueDeser);
    final JavaType vt = _collectionType.getContentType();
    if (valueDeser == null) {
        valueDeser = ctxt.findContextualValueDeserializer(vt, property);
    } else {
        valueDeser = ctxt.handleSecondaryContextualization(valueDeser, property, vt);
    }

    // Contextualize the type deserializer if present
    TypeDeserializer valueTypeDeser = _valueTypeDeserializer;
    if (valueTypeDeser != null) {
        valueTypeDeser = valueTypeDeser.forProperty(property);
    }

    return withResolved(delegateDeser, valueDeser, valueTypeDeser, unwrapSingle);
}
