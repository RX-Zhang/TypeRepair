public JsonSerializer<?> createContextual(SerializerProvider serializers,
        BeanProperty property) throws JsonMappingException
{
    if (property != null) {
        JsonFormat.Value format = serializers.getAnnotationIntrospector().findFormat((Annotated)property.getMember());
        if (format != null) {

        	// Determine shape, falling back to default handling if SHAPE.ANY
            JsonFormat.Shape shape = format.getShape();
            if (shape == JsonFormat.Shape.ANY) {
                // Use _asTimestamp to determine whether to serialize as timestamp or string
                if (_asTimestamp(serializers)) {
                    return withFormat(Boolean.TRUE, null);
                }
                // use pattern/locale/timezone if provided
                if (format.hasPattern() || format.hasLocale() || format.getTimeZone() != null) {
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
                return withFormat(Boolean.FALSE, null);
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
