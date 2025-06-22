public Period normalizedStandard(PeriodType type) {
    type = DateTimeUtils.getPeriodType(type);
    // Convert supported fields to milliseconds
    long millis = 0;
    if (type.isSupported(DurationFieldType.millis())) {
        millis += getMillis();
    }
    if (type.isSupported(DurationFieldType.seconds())) {
        millis += ((long) getSeconds()) * DateTimeConstants.MILLIS_PER_SECOND;
    }
    if (type.isSupported(DurationFieldType.minutes())) {
        millis += ((long) getMinutes()) * DateTimeConstants.MILLIS_PER_MINUTE;
    }
    if (type.isSupported(DurationFieldType.hours())) {
        millis += ((long) getHours()) * DateTimeConstants.MILLIS_PER_HOUR;
    }
    if (type.isSupported(DurationFieldType.days())) {
        millis += ((long) getDays()) * DateTimeConstants.MILLIS_PER_DAY;
    }
    if (type.isSupported(DurationFieldType.weeks())) {
        millis += ((long) getWeeks()) * DateTimeConstants.MILLIS_PER_WEEK;
    }

    Period result = new Period(millis, type, ISOChronology.getInstanceUTC());

    int years = getYears();
    int months = getMonths();
    if (years != 0 || months != 0) {
        years = FieldUtils.safeAdd(years, months / 12);
        months = months % 12;
        if (years != 0) {
            if (type.isSupported(DurationFieldType.years())) {
                result = result.withYears(years);
            } else {
                throw new UnsupportedOperationException("Field 'years' is not supported");
            }
        }
        if (months != 0) {
            if (type.isSupported(DurationFieldType.months())) {
                result = result.withMonths(months);
            } else {
                throw new UnsupportedOperationException("Field 'months' is not supported");
            }
        }
    }
    return result;
}
