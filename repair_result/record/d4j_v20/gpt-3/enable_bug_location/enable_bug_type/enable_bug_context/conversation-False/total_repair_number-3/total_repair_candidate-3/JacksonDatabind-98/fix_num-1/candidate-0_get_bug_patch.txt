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
                    prop.getName(), extProp.getTypePropertyName());
        }
        values[i] = _deserialize(p, ctxt, i, typeId);

        final SettableBeanProperty prop = extProp.getProperty();

        // [databind#1328]: If type id property is not String, convert accordingly
        SettableBeanProperty typeProp = extProp.getTypeProperty();

        if (prop.getCreatorIndex() >= 0) {
            buffer.assignParameter(prop, values[i]);

            if (typeProp != null && typeProp.getCreatorIndex() >= 0) {
                Object typedIdValue = typeId;
                JavaType typeIdType = typeProp.getType();

                // Convert typeId String to expected type if necessary
                if (!typeIdType.hasRawClass(String.class)) {
                    try {
                        // Use the deserializer to convert type id String to expected type
                        JsonParser typeIdParser = ctxt.getParser().getCodec()
                                .getFactory().createParser("\""+typeId+"\"");
                        typeIdParser.nextToken(); // move to VALUE_STRING
                        typedIdValue = typeProp.deserialize(typeIdParser, ctxt);
                    } catch (Exception e) {
                        // fallback to typeId string if conversion fails
                        typedIdValue = typeId;
                    }
                }
                buffer.assignParameter(typeProp, typedIdValue);
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
