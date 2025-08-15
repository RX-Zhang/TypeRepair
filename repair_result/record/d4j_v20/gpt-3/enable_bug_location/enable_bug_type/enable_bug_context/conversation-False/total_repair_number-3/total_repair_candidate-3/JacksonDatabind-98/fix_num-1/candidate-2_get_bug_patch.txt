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
            // 26-Oct-2012, tatu: As per [databind#94], must allow use of 'defaultImpl'
            if (!extProp.hasDefaultType()) {
                ctxt.reportInputMismatch(_beanType,
                        "Missing external type id property '%s'",
                        extProp.getTypePropertyName());
            } else {
                typeId = extProp.getDefaultTypeId();
            }
        } else if (_tokens[i] == null) {
            SettableBeanProperty prop = extProp.getProperty();
            ctxt.reportInputMismatch(_beanType,
                    "Missing property '%s' for external type id '%s'",
                    prop.getName(), _properties[i].getTypePropertyName());
        }
        Object value = _deserialize(p, ctxt, i, typeId);

        // Convert typeId string to expected type if the type property creator parameter expects non-String
        final SettableBeanProperty prop = extProp.getProperty();
        if (prop.getCreatorIndex() >= 0) {
            buffer.assignParameter(prop, value);

            // [databind#999] And maybe there's creator property for type id too?
            SettableBeanProperty typeProp = extProp.getTypeProperty();
            if ((typeProp != null) && (typeProp.getCreatorIndex() >= 0)) {
                Object typedTypeId = typeId;
                // Check if the type property expects a type other than String:
                if (!typeProp.getType().hasRawClass(String.class)) {
                    // Convert string typeId to expected type using proper deserializer
                    JsonParser typeIdParser = ctxt.getParser().getCodec().treeAsTokens(ctxt.getConfig().getNodeFactory().textNode(typeId));
                    typedTypeId = typeProp.deserialize(typeIdParser, ctxt);
                }
                buffer.assignParameter(typeProp, typedTypeId);
            }
        }
        values[i] = value;
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
