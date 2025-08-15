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
        } else {
            Object ser = findSerializer(a);
            JsonView jv = _findAnnotation(a, JsonView.class);
            JsonRawValue jrv = _findAnnotation(a, JsonRawValue.class);
            if (ser != null || jv != null || (jrv != null && jrv.value())) {
                name = "";
            } else {
                return null;
            }
        }
    }
    return PropertyName.construct(name);
}
