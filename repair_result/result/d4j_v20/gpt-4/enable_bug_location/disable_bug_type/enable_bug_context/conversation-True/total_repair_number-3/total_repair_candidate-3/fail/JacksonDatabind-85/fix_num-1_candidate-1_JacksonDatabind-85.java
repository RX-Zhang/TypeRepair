public JsonSerializer<?> createContextual(SerializerProvider serializers,
        BeanProperty property) throws JsonMappingException
{
    if (property == null) {
        return this;
    }
    JsonFormat.Value format = findFormatOverrides(serializers, property, handledType());
    if (format == null) {
        return this;
    }
    // Simple case first: serialize as numeric timestamp?
    JsonFormat.Shape shape = format.getShape();
    if (shape.isNumeric()) {
        return withFormat(Boolean.TRUE, null);
    }

    // 08-Jun-2017, tatu: With [databind#1648], this gets bit tricky..
    // First: custom pattern will override things
    if ((shape == JsonFormat.Shape.STRING) || format.hasPattern()
                    || format.hasLocale() || format.hasTimeZone()) {
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

    // Otherwise, need one of these changes:

    DateFormat df = serializers.getConfig().getDateFormat();
    if (df.getClass() == StdDateFormat.class) {
        // StdDateFormat is immutable so can usually just use as is
        return withFormat(Boolean.FALSE, df);
    }

    if (df instanceof SimpleDateFormat) {
        // Clone or create new with same pattern and timezone
        SimpleDateFormat sdf = (SimpleDateFormat) df;
        String pattern = sdf.toPattern();
        SimpleDateFormat newDf = new SimpleDateFormat(pattern, sdf.getDateFormatSymbols());
        newDf.setTimeZone(sdf.getTimeZone());
        return withFormat(Boolean.FALSE, newDf);
    }

    // Cannot change configuration: fail or just return this
    return this;
}
