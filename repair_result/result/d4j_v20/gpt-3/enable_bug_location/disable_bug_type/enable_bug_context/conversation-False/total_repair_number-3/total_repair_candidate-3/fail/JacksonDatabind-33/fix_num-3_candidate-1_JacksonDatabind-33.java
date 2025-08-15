public PropertyName findNameForSerialization(Annotated a)
{
    String name = null;

    JsonGetter jg = _findAnnotation(a, JsonGetter.class);
    if (jg != null) {
        // Handle empty value as no override
        String jgValue = jg.value();
        if (jgValue != null && !jgValue.isEmpty()) {
            name = jgValue;
        }
    } 
    if (name == null) {
        JsonProperty pann = _findAnnotation(a, JsonProperty.class);
        if (pann != null) {
            String pannValue = pann.value();
            if (pannValue != null && !pannValue.isEmpty()) {
                name = pannValue;
            } else {
                // If empty, this means to not rename, but explicitly mark property to be serialized
                name = "";
            }
            /* 22-Apr-2014, tatu: Should figure out a better way to do this, but
             *   it's actually bit tricky to do it more efficiently (meta-annotations
             *   add more lookups; AnnotationMap costs etc)
             */
        } else if (_hasAnnotation(a, JsonSerialize.class)
                || _hasAnnotation(a, JsonView.class)
                || _hasAnnotation(a, JsonRawValue.class)) {
            name = "";
        } else {
            return null;
        }
    }
    return PropertyName.construct(name);
}
