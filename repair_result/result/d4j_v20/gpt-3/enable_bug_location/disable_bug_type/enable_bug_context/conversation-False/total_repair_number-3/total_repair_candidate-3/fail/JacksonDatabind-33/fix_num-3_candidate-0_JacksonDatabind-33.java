public PropertyName findNameForSerialization(Annotated a)
{
    String name = null;

    JsonGetter jg = _findAnnotation(a, JsonGetter.class);
    if (jg != null) {
        name = jg.value();
    } else {
        JsonProperty pann = _findAnnotation(a, JsonProperty.class);
        if (pann != null) {
            name = pann.value();
            /* 22-Apr-2014, tatu: Should figure out a better way to do this, but
             *   it's actually bit tricky to do it more efficiently (meta-annotations
             *   add more lookups; AnnotationMap costs etc)
             */
        } else if (_hasAnnotation(a, JsonSerialize.class)
                || _hasAnnotation(a, JsonView.class)
                || _hasAnnotation(a, JsonRawValue.class)) {
            // Use default name if annotation present but no explicit name set
            name = "";
        } else {
            return null;
        }
    }
    if (name == null || name.isEmpty()) {
        // Means no explicit name; get default name from annotated element
        name = a.getName();
    }
    return PropertyName.construct(name);
}
