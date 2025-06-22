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

    // Jackson's own `StdDateFormat` is quite easy to deal with...

    // 08-Jun-2017, tatu: Unfortunately there's no generally usable
    //    mechanism for changing `DateFormat` instances (or even clone()ing)
    //    So: require it be `SimpleDateFormat`; can't config other types
    if (_customFormat != null) { // do not try to modify custom formats we do not own
        return this;
    }
    if (serializers.getDateFormat() instanceof SimpleDateFormat) {
        SimpleDateFormat df = (SimpleDateFormat) serializers.getDateFormat();
        String pattern = df.toPattern();
        Locale loc = df.getDateFormatSymbols() != null ? df.getDateFormatSymbols().getLocale() : null;
        TimeZone tz = df.getTimeZone();

        if (format.hasPattern()) {
            pattern = format.getPattern();
        }
        if (format.hasLocale()) {
            loc = format.getLocale();
        }
        if (format.hasTimeZone()) {
            tz = format.getTimeZone();
        }
        SimpleDateFormat newDf = new SimpleDateFormat(pattern, loc);
        newDf.setTimeZone(tz);
        return withFormat(Boolean.FALSE, newDf);
    }

    // Falls back to default behavior; cannot change config
    return this;
}
