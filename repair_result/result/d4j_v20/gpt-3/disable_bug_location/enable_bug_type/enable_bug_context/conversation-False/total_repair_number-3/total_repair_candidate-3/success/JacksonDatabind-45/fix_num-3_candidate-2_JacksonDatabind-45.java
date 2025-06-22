public JsonSerializer<?> createContextual(SerializerProvider serializers,
        BeanProperty property) throws JsonMappingException
{
    if (property != null && serializers != null) {
        JsonFormat.Value format = serializers.getAnnotationIntrospector().findFormat((Annotated)property.getMember());
        if (format != null) {

            // Simple case first: serialize as numeric timestamp?
            JsonFormat.Shape shape = format.getShape();
            if (shape.isNumeric()) {
                return withFormat(Boolean.TRUE, null);
            }

            // Check if pattern, locale, or timezone is explicitly set; if so, treat as STRING shape
            boolean hasPattern = format.hasPattern();
            boolean hasLocale = format.hasLocale();
            boolean hasTimeZone = format.hasTimeZone();

            // If shape is STRING or any of pattern/locale/timezone is set, force string serialization
            if (shape == JsonFormat.Shape.STRING || hasPattern || hasLocale || hasTimeZone) {
                TimeZone tz = format.getTimeZone();
                final String pattern = hasPattern
                                ? format.getPattern()
                                : StdDateFormat.DATE_FORMAT_STR_ISO8601;
                final Locale loc = hasLocale
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
