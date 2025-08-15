public JsonSerializer<?> createContextual(SerializerProvider serializers,
        BeanProperty property) throws JsonMappingException
{
    if (property != null) {
        JsonFormat.Value format = serializers.getAnnotationIntrospector().findFormat((Annotated)property.getMember());
        if (format != null) {

        	// Simple case first: serialize as numeric timestamp?
            JsonFormat.Shape shape = format.getShape();
            if (shape.isNumeric()) {
                return withFormat(Boolean.TRUE, null);
            }

            if (shape == JsonFormat.Shape.ANY) {
                // No explicit shape: use default timestamp setting as is
                return withFormat(null, null);
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
