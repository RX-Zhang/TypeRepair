protected void addBeanProps(DeserializationContext ctxt,
        BeanDescription beanDesc, BeanDeserializerBuilder builder)
    throws JsonMappingException
{
    final boolean isConcrete = !beanDesc.getType().isAbstract();
    final SettableBeanProperty[] creatorProps = isConcrete
            ? builder.getValueInstantiator().getFromObjectArguments(ctxt.getConfig())
            : null;
    final boolean hasCreatorProps = (creatorProps != null);
    
    // 01-May-2016, tatu: Which base type to use here gets tricky, since
    //   it may often make most sense to use general type for overrides,
    //   but what we have here may be more specific impl type. But for now
    //   just use it as is.
    JsonIgnoreProperties.Value ignorals = ctxt.getConfig()
            .getDefaultPropertyIgnorals(beanDesc.getBeanClass(),
                    beanDesc.getClassInfo());
    Set<String> ignored;

    if (ignorals != null) {
        boolean ignoreAny = ignorals.getIgnoreUnknown();
        builder.setIgnoreUnknownProperties(ignoreAny);
        // Or explicit/implicit definitions?
        ignored = ignorals.getIgnored();
        for (String propName : ignored) {
            builder.addIgnorable(propName);
        }
    } else {
        ignored = Collections.emptySet();
    }

    // Also, do we have a fallback "any" setter?
    AnnotatedMethod anySetterMethod = beanDesc.findAnySetter();
    AnnotatedMember anySetterField = null;
    if (anySetterMethod != null) {
        builder.setAnySetter(constructAnySetter(ctxt, beanDesc, anySetterMethod));
    }
    else {
        anySetterField = beanDesc.findAnySetterField();
        if(anySetterField != null) {
            builder.setAnySetter(constructAnySetter(ctxt, beanDesc, anySetterField));
        }
    }
    // NOTE: we do NOT add @JsonIgnore'd properties into blocked ones if there's any-setter
    // Implicit ones via @JsonIgnore and equivalent?
    if (anySetterMethod == null && anySetterField == null) {
        Collection<String> ignored2 = beanDesc.getIgnoredPropertyNames();
        if (ignored2 != null) {
            for (String propName : ignored2) {
                // allow ignoral of similarly named JSON property, but do not force;
                // latter means NOT adding this to 'ignored':
                builder.addIgnorable(propName);
            }
        }
    }
    final boolean useGettersAsSetters = ctxt.isEnabled(MapperFeature.USE_GETTERS_AS_SETTERS)
            && ctxt.isEnabled(MapperFeature.AUTO_DETECT_GETTERS);

    // Ok: let's then filter out property definitions
    List<BeanPropertyDefinition> propDefs = filterBeanProps(ctxt,
            beanDesc, builder, beanDesc.findProperties(), ignored);

    // After which we can let custom code change the set
    if (_factoryConfig.hasDeserializerModifiers()) {
        for (BeanDeserializerModifier mod : _factoryConfig.deserializerModifiers()) {
            propDefs = mod.updateProperties(ctxt.getConfig(), beanDesc, propDefs);
        }
    }
    
    // At which point we still have all kinds of properties; not all with mutators:
    for (BeanPropertyDefinition propDef : propDefs) {
        SettableBeanProperty prop = null;

        if (propDef.hasSetter()) {
            JavaType propertyType = propDef.getSetter().getParameterType(0);
            prop = constructSettableProperty(ctxt, beanDesc, propDef, propertyType);
        } else if (propDef.hasField()) {
            JavaType propertyType = propDef.getField().getType();
            prop = constructSettableProperty(ctxt, beanDesc, propDef, propertyType);
        } else if (useGettersAsSetters && propDef.hasGetter()) {
            // should only consider Collections and Maps, for now?
            AnnotatedMethod getter = propDef.getGetter();
            Class<?> rawPropertyType = getter.getRawType();
            if (Collection.class.isAssignableFrom(rawPropertyType)
                    || Map.class.isAssignableFrom(rawPropertyType)) {
                prop = constructSetterlessProperty(ctxt, beanDesc, propDef);
            }
        }

        // 25-Sep-2014, tatu: No point in finding constructor parameters for abstract types
        //   (since they are never used anyway)
        if (hasCreatorProps && propDef.hasConstructorParameter()) {
            final String name = propDef.getName();
            CreatorProperty cprop = null;
            if (creatorProps != null) {
                for (SettableBeanProperty cp : creatorProps) {
                    if (name.equals(cp.getName()) && (cp instanceof CreatorProperty)) {
                        cprop = (CreatorProperty) cp;
                        break;
                    }
                }
            }
            if (cprop == null) {
                List<String> n = new ArrayList<>();
                for (SettableBeanProperty cp : creatorProps) {
                    n.add(cp.getName());
                }
                ctxt.reportBadPropertyDefinition(beanDesc, propDef,
                        "Could not find creator property with name '%s' (known Creator properties: %s)",
                        name, n);
                continue;
            }
            if (prop != null) {
                cprop.setFallbackSetter(prop);
            }
            prop = cprop;
            builder.addCreatorProperty(cprop);
            continue;
        }

        if (prop != null) {
            Class<?>[] views = propDef.findViews();
            if (views == null) {
                if (!ctxt.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION)) {
                    views = NO_VIEWS;
                }
            }
            // one more thing before adding to builder: copy any metadata
            prop.setViews(views);
            builder.addProperty(prop);
        }
    }
}
