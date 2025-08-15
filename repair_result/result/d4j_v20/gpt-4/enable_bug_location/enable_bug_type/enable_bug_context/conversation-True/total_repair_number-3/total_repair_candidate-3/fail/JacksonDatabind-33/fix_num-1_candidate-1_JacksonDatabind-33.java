public PropertyName findNameForSerialization(Annotated a)
{
    String name = null;

    JsonGetter jg = _findAnnotation(a, JsonGetter.class);
    if (jg != null) {
        name = jg.value();
    } else {
        JsonProperty pann = _findAnnotation(a, JsonProperty.class);
        if (pann != null) {
            // If JsonProperty value is empty, treat it as no explicit name to allow default handling
            if (!pann.value().isEmpty()) {
                name = pann.value();
            } else {
                // Explicitly treat empty value as null to allow default name usage
                name = null;
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
    return (name == null) ? null : PropertyName.construct(name);
}
