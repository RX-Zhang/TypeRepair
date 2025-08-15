public PropertyName findNameForSerialization(Annotated a)
{
    Object ser = findSerializer(a);
    if (ser != null) {
        // If there's a serializer and it's not a default empty serializer
        return PropertyName.construct("");
    }
    
    String name = null;

    JsonGetter jg = _findAnnotation(a, JsonGetter.class);
    if (jg != null) {
        name = jg.value();
    } else {
        JsonProperty pann = _findAnnotation(a, JsonProperty.class);
        if (pann != null) {
            name = pann.value();
        } else if (_hasAnnotation(a, JsonView.class)) {
            // keep this to avoid returning null unnecessarily if JsonView is present
            name = "";
        } else {
            return null;
        }
    }
    return PropertyName.construct(name);
}
