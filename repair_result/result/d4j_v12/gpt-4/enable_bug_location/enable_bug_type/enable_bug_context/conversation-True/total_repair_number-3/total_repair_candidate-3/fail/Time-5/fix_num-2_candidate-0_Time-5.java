public Period normalizedStandard(PeriodType type) {
    type = DateTimeUtils.getPeriodType(type);
    
    // Convert fields that can be directly converted into milliseconds
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

    // Create a Period from milliseconds for supported fields
    Period result = new Period(millis, type, ISOChronology.getInstanceUTC());

    // Handle years and months separately because they cannot be normalized into millis easily
    int years = getYears();
    int months = getMonths();

    // Normalize months and years
    if (years != 0 || months != 0) {
        years = FieldUtils.safeAdd(years, months / 12);
        months = months % 12;

        if (type.isSupported(DurationFieldType.years())) {
            if (years != 0) {
                result = result.withYears(years);
            }
        } else if (years != 0) {
            throw new UnsupportedOperationException("Field 'years' is not supported by the period type");
        }

        if (type.isSupported(DurationFieldType.months())) {
            if (months != 0) {
                result = result.withMonths(months);
            }
        } else if (months != 0) {
            throw new UnsupportedOperationException("Field 'months' is not supported by the period type");
        }
    }

    return result;
}
