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
        }
    }
    // [databind#1043]: allow per-property allow-wrapping of single overrides:
    Boolean unwrapSingle = findFormatFeature(ctxt, property, Collection.class,
            JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    // also, often value deserializer is resolved here:
    JsonDeserializer<?> valueDeser = _valueDeserializer;

    // May have a content converter
    valueDeser = findConvertingContentDeserializer(ctxt, property, valueDeser);
    final JavaType vt = _collectionType.getContentType();
    if (valueDeser == null) {
        valueDeser = ctxt.findContextualValueDeserializer(vt, property);
    } else { // if directly assigned, probably not yet contextual, so:
        valueDeser = ctxt.handleSecondaryContextualization(valueDeser, property, vt);
    }
    // and finally, type deserializer needs context as well
    TypeDeserializer valueTypeDeser = _valueTypeDeserializer;
    if (valueTypeDeser != null) {
        valueTypeDeser = valueTypeDeser.forProperty(property);
    }

    try {
        return withResolved(delegateDeser, valueDeser, valueTypeDeser, unwrapSingle);
    } catch (IllegalStateException e) {
        // Defensive handling for cases like no default constructor for collection type
        // fall back to default withResolved without delegate deserializer if possible
        if (delegateDeser != null) {
            return withResolved(null, valueDeser, valueTypeDeser, unwrapSingle);
        }
        throw e;
    }
}
