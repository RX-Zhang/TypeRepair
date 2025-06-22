public JsonSerializer<?> createContextual(SerializerProvider serializers,
        BeanProperty property) throws JsonMappingException
{
    if (property != null) {
        JsonFormat.Value format = serializers.getAnnotationIntrospector().findFormat((Annotated)property.getMember());
        if (format != null) {

            // we must determine shape to use; if shape == ANY (default), rely on user config
            JsonFormat.Shape shape = format.getShape();
            if (shape == JsonFormat.Shape.ANY) {
                // if pattern, locale, or timezone specified, should serialize as string regardless
                if (format.hasPattern() || format.hasLocale() || format.hasTimeZone()) {
                    shape = JsonFormat.Shape.STRING;
                }
            }

            if (shape.isNumeric()) {
                return withFormat(Boolean.TRUE, null);
            }

            if (shape == JsonFormat.Shape.STRING) {
                TimeZone tz = format.getTimeZone();
                final String pattern = format.hasPattern()
                                ? format.getPattern()
                                : StdDateFormat.DATE_FORMAT_STR_ISO8601;
                final Locale loc = format.hasLocale()
                                ? format.getLocale()
                                : serializers.getLocale();
                SimpleDateFormat df = new SimpleDateFormat(pattern, loc);
                if (tz == null) {
                    tz = serializers.getTimeZone();
                }
                df.setTimeZone(tz);
                return withFormat(Boolean.FALSE, df);
            }
        }
    }
    return this;
}
