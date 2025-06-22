public Object complete(JsonParser p, DeserializationContext ctxt,
        PropertyValueBuffer buffer, PropertyBasedCreator creator)
    throws IOException
{
    // first things first: deserialize all data buffered:
    final int len = _properties.length;
    Object[] values = new Object[len];
    for (int i = 0; i < len; ++i) {
        String typeId = _typeIds[i];
        final ExtTypedProperty extProp = _properties[i];
        if (typeId == null) {
            // let's allow missing both type and property (may already have been set, too)
            if (_tokens[i] == null) {
                continue;
            }
            // but not just one
            // need to check for natural types or default impl
            JsonToken t = _tokens[i].firstToken();
            if (t.isScalarValue()) {
                JsonParser buffered = _tokens[i].asParser(p);
                buffered.nextToken();
                SettableBeanProperty prop = extProp.getProperty();
                Object result = TypeDeserializer.deserializeIfNatural(buffered, ctxt, prop.getType());
                if (result != null) {
                    values[i] = result;
                    // also: if it's creator prop, fill in
                    if (prop.getCreatorIndex() >= 0) {
                        buffer.assignParameter(prop, result);

                        SettableBeanProperty typeProp = extProp.getTypeProperty();
                        if ((typeProp != null) && (typeProp.getCreatorIndex() >= 0)) {
                            buffer.assignParameter(typeProp, null);
                        }
                    }
                    continue;
                }
            }
            if (!extProp.hasDefaultType()) {
                ctxt.reportInputMismatch(_beanType,
                        "Missing external type id property '%s'",
                        extProp.getTypePropertyName());
            } else {
                typeId = extProp.getDefaultTypeId();
            }
        } else if (_tokens[i] == null) {
            SettableBeanProperty prop = extProp.getProperty();
            if(prop.isRequired() ||
                    ctxt.isEnabled(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY)) {
                ctxt.reportInputMismatch(_beanType,
                        "Missing property '%s' for external type id '%s'",
                        prop.getName(), extProp.getTypePropertyName());
            }
            // no value to assign; set null
            if (prop.getCreatorIndex() >= 0) {
                buffer.assignParameter(prop, null);
                SettableBeanProperty typeProp = extProp.getTypeProperty();
                if ((typeProp != null) && (typeProp.getCreatorIndex() >= 0)) {
                    buffer.assignParameter(typeProp, typeId);
                }
            }
            continue;
        }
        // only deserialize if not set already by natural type handling above
        if (values[i] == null) {
            values[i] = _deserialize(p, ctxt, i, typeId);
            final SettableBeanProperty prop = extProp.getProperty();
            if (prop.getCreatorIndex() >= 0) {
                buffer.assignParameter(prop, values[i]);

                SettableBeanProperty typeProp = extProp.getTypeProperty();
                if ((typeProp != null) && (typeProp.getCreatorIndex() >= 0)) {
                    buffer.assignParameter(typeProp, typeId);
                }
            }
        }
    }
    Object bean = creator.build(ctxt, buffer);
    // third: assign non-creator properties
    for (int i = 0; i < len; ++i) {
        SettableBeanProperty prop = _properties[i].getProperty();
        if (prop.getCreatorIndex() < 0) {
            prop.set(bean, values[i]);
        }
    }
    return bean;
}
