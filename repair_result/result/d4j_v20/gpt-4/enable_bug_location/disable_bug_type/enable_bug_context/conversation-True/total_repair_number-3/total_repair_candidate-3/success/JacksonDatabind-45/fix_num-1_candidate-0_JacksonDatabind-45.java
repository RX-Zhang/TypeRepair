public JsonSerializer<?> createContextual(SerializerProvider serializers,
        BeanProperty property) throws JsonMappingException
{
    if (property != null) {
        JsonFormat.Value format = serializers.getAnnotationIntrospector().findFormat((Annotated)property.getMember());
        if (format != null) {

            // Determine shape explicitly; fall back to default behavior if Shape.ANY
            JsonFormat.Shape shape = format.getShape();
            if (shape == JsonFormat.Shape.ANY) {
                // Use timestamp or string based on user config and presence of pattern/locale/timezone
                if (format.hasPattern() || format.hasLocale() || format.hasTimeZone()) {
                    shape = JsonFormat.Shape.STRING;
                } else {
                    return withFormat(null, null);
                }
            }

            // Serialize as numeric timestamp
            if (shape.isNumeric()) {
                return withFormat(Boolean.TRUE, null);
            }

            // Serialize as string
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
