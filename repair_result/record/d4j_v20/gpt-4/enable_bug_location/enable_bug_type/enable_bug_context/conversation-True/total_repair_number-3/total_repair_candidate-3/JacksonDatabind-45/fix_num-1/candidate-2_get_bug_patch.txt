public JsonSerializer<?> createContextual(SerializerProvider serializers,
        BeanProperty property) throws JsonMappingException
{
    if (property != null) {
        JsonFormat.Value format = serializers.getAnnotationIntrospector().findFormat((Annotated)property.getMember());
        if (format != null) {

            // Determine shape considering @JsonFormat and user config
            JsonFormat.Shape shape = format.getShape();
            if (shape == JsonFormat.Shape.ANY) {
                // shape ANY means defer to user settings unless pattern/locale/timezone present
                boolean hasPattern = format.hasPattern();
                boolean hasLocale = format.hasLocale();
                boolean hasTimeZone = format.getTimeZone() != null;
                if (hasPattern || hasLocale || hasTimeZone) {
                    // Use string shape due to pattern/locale/timezone
                    shape = JsonFormat.Shape.STRING;
                } else {
                    // Else defer to user config (timestamp or not)
                    return this;
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
