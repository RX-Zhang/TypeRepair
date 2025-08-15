public Period normalizedStandard(PeriodType type) {
    type = DateTimeUtils.getPeriodType(type);

    // Convert fields that can be converted to millis
    long millis = 0L;
    if (type.isSupported(DurationFieldType.millis())) {
        millis += getMillis();
    } else if (getMillis() != 0) {
        throw new UnsupportedOperationException("Field 'millis' is not supported");
    }
    if (type.isSupported(DurationFieldType.seconds())) {
        millis += ((long) getSeconds()) * DateTimeConstants.MILLIS_PER_SECOND;
    } else if (getSeconds() != 0) {
        throw new UnsupportedOperationException("Field 'seconds' is not supported");
    }
    if (type.isSupported(DurationFieldType.minutes())) {
        millis += ((long) getMinutes()) * DateTimeConstants.MILLIS_PER_MINUTE;
    } else if (getMinutes() != 0) {
        throw new UnsupportedOperationException("Field 'minutes' is not supported");
    }
    if (type.isSupported(DurationFieldType.hours())) {
        millis += ((long) getHours()) * DateTimeConstants.MILLIS_PER_HOUR;
    } else if (getHours() != 0) {
        throw new UnsupportedOperationException("Field 'hours' is not supported");
    }
    if (type.isSupported(DurationFieldType.days())) {
        millis += ((long) getDays()) * DateTimeConstants.MILLIS_PER_DAY;
    } else if (getDays() != 0) {
        throw new UnsupportedOperationException("Field 'days' is not supported");
    }
    if (type.isSupported(DurationFieldType.weeks())) {
        millis += ((long) getWeeks()) * DateTimeConstants.MILLIS_PER_WEEK;
    } else if (getWeeks() != 0) {
        throw new UnsupportedOperationException("Field 'weeks' is not supported");
    }

    Period result = new Period(millis, type, ISOChronology.getInstanceUTC());

    // Handle years and months separately if supported
    int years = getYears();
    int months = getMonths();

    if (type.isSupported(DurationFieldType.years()) || type.isSupported(DurationFieldType.months())) {
        years = FieldUtils.safeAdd(years, months / 12);
        months = months % 12;

        if (type.isSupported(DurationFieldType.years())) {
            result = result.withYears(years);
        } else if (years != 0) {
            throw new UnsupportedOperationException("Field 'years' is not supported");
        }

        if (type.isSupported(DurationFieldType.months())) {
            result = result.withMonths(months);
        } else if (months != 0) {
            throw new UnsupportedOperationException("Field 'months' is not supported");
        }
    } else {
        // If type does not support years or months but they exist, throw exception
        if (years != 0 || months != 0) {
            throw new UnsupportedOperationException("Field 'years' and/or 'months' are not supported");
        }
    }

    return result;
}
