public Period normalizedStandard(PeriodType type) {
    type = DateTimeUtils.getPeriodType(type);
    long millis = getMillis();  // no overflow can happen, even with Integer.MAX_VALUEs
    millis += (((long) getSeconds()) * ((long) DateTimeConstants.MILLIS_PER_SECOND));
    millis += (((long) getMinutes()) * ((long) DateTimeConstants.MILLIS_PER_MINUTE));
    millis += (((long) getHours()) * ((long) DateTimeConstants.MILLIS_PER_HOUR));
    millis += (((long) getDays()) * ((long) DateTimeConstants.MILLIS_PER_DAY));
    millis += (((long) getWeeks()) * ((long) DateTimeConstants.MILLIS_PER_WEEK));
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
                throw new UnsupportedOperationException("Years field is not supported");
            }
        }
        if (months != 0) {
            if (type.isSupported(DurationFieldType.months())) {
                result = result.withMonths(months);
            } else {
                throw new UnsupportedOperationException("Months field is not supported");
            }
        }
    }

    return result.withPeriodType(type);
}
