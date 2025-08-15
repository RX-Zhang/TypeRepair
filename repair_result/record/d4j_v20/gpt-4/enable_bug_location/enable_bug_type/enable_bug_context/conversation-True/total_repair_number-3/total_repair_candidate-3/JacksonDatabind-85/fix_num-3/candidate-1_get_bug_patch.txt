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

    // Otherwise, if shape is default and no pattern/locale/timezone changes, just use default format if available
    // But we must handle if the default serializer's _customFormat is a SimpleDateFormat and adjust it accordingly

    DateFormat baseFormat = serializers.getConfig().getDateFormat();
    if (baseFormat instanceof SimpleDateFormat) {
        SimpleDateFormat sdf = (SimpleDateFormat) baseFormat;
        // If there's no specific pattern/locale/timezone override, use the baseFormat as is
        return withFormat(Boolean.FALSE, (DateFormat) sdf.clone());
    }

    // Can't change non-SimpleDateFormat (like StdDateFormat)
    serializers.reportBadDefinition(handledType(), String.format(
        "Cannot contextualize date serializer: expected SimpleDateFormat, got %s",
        baseFormat.getClass().getName()));
    return this;
}
