public JsonSerializer<?> createContextual(SerializerProvider serializers,
        BeanProperty property) throws JsonMappingException
{
    if (property != null) {
        JsonFormat.Value format = serializers.getAnnotationIntrospector().findFormat((Annotated)property.getMember());
        if (format != null) {

        	// Determine shape to use for serialization
            JsonFormat.Shape shape = format.getShape();

            // If shape is ANY, resolve based on presence of pattern, locale or timezone
            if (shape == JsonFormat.Shape.ANY) {
                if (format.hasPattern() || format.hasLocale() || format.getTimeZone() != null) {
                    shape = JsonFormat.Shape.STRING;
                } else {
                    shape = _asTimestamp(serializers) ? JsonFormat.Shape.NUMBER_INT : JsonFormat.Shape.STRING;
                }
            }

        	// Simple case first: serialize as numeric timestamp?
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
