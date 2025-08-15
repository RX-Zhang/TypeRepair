public PropertyName findNameForSerialization(Annotated a)
{
    String name = null;

    JsonGetter jg = _findAnnotation(a, JsonGetter.class);
    if (jg != null) {
        name = jg.value();
        if (name.isEmpty()) {
            // If JsonGetter's value is empty, treat as explicit empty name
            return PropertyName.USE_DEFAULT;
        }
    } else {
        JsonProperty pann = _findAnnotation(a, JsonProperty.class);
        if (pann != null) {
            name = pann.value();
            if (name.isEmpty() && !pann.required()) {
                // Empty JsonProperty means explicit empty name only if not required
                return PropertyName.USE_DEFAULT;
            }
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
