protected BasePeriod(long duration) {
    this(duration, null, null);
    // bug [3264409]
    // Fix: explicitly set the period fields to correctly represent the duration
    // using setPeriod with all fields set to zero except hours, minutes, etc.
    long remaining = duration;
    int years = 0;
    int months = 0;
    int weeks = 0;
    int days = 0;
    int hours = (int) (remaining / DateTimeConstants.MILLIS_PER_HOUR);
    remaining -= hours * DateTimeConstants.MILLIS_PER_HOUR;
    int minutes = (int) (remaining / DateTimeConstants.MILLIS_PER_MINUTE);
    remaining -= minutes * DateTimeConstants.MILLIS_PER_MINUTE;
    int seconds = (int) (remaining / DateTimeConstants.MILLIS_PER_SECOND);
    remaining -= seconds * DateTimeConstants.MILLIS_PER_SECOND;
    int millis = (int) remaining;
    setPeriod(years, months, weeks, days, hours, minutes, seconds, millis);
}
