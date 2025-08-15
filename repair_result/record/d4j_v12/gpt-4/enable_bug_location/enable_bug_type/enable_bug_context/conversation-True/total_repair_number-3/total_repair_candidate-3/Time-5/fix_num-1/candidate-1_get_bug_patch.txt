public Period normalizedStandard(PeriodType type) {
    type = DateTimeUtils.getPeriodType(type);
    long millis = 0L;
    if (type.isSupported(DurationFieldType.millis())) {
        millis += getMillis();
    } else if (getMillis() != 0) {
        throw new UnsupportedOperationException("Field is not supported");
    }
    if (type.isSupported(DurationFieldType.seconds())) {
        millis += ((long) getSeconds()) * DateTimeConstants.MILLIS_PER_SECOND;
    } else if (getSeconds() != 0) {
        throw new UnsupportedOperationException("Field is not supported");
    }
    if (type.isSupported(DurationFieldType.minutes())) {
        millis += ((long) getMinutes()) * DateTimeConstants.MILLIS_PER_MINUTE;
    } else if (getMinutes() != 0) {
        throw new UnsupportedOperationException("Field is not supported");
    }
    if (type.isSupported(DurationFieldType.hours())) {
        millis += ((long) getHours()) * DateTimeConstants.MILLIS_PER_HOUR;
    } else if (getHours() != 0) {
        throw new UnsupportedOperationException("Field is not supported");
    }
    if (type.isSupported(DurationFieldType.days())) {
        millis += ((long) getDays()) * DateTimeConstants.MILLIS_PER_DAY;
    } else if (getDays() != 0) {
        throw new UnsupportedOperationException("Field is not supported");
    }
    if (type.isSupported(DurationFieldType.weeks())) {
        millis += ((long) getWeeks()) * DateTimeConstants.MILLIS_PER_WEEK;
    } else if (getWeeks() != 0) {
        throw new UnsupportedOperationException("Field is not supported");
    }

    Period result = new Period(millis, type, ISOChronology.getInstanceUTC());

    int years = 0;
    int months = 0;
    if (type.isSupported(DurationFieldType.years())) {
        years = getYears();
    } else if (getYears() != 0) {
        throw new UnsupportedOperationException("Field is not supported");
    }
    if (type.isSupported(DurationFieldType.months())) {
        months = getMonths();
    } else if (getMonths() != 0) {
        throw new UnsupportedOperationException("Field is not supported");
    }

    if (years != 0 || months != 0) {
        years = FieldUtils.safeAdd(years, months / 12);
        months = months % 12;
        if (years != 0) {
            result = result.withYears(years);
        }
        if (months != 0) {
            result = result.withMonths(months);
        }
    }
    return result;
}
