protected BasePeriod(long duration) {
    this(duration, null, null);
    // bug [3264409]
    // Fix: Convert total duration to period fields explicitly to avoid misinterpretation of weeks
    long remainder = duration;

    int years = (int) (remainder / (DateTimeConstants.MILLIS_PER_DAY * 365));
    remainder -= years * DateTimeConstants.MILLIS_PER_DAY * 365;

    int months = (int) (remainder / (DateTimeConstants.MILLIS_PER_DAY * 30));
    remainder -= months * DateTimeConstants.MILLIS_PER_DAY * 30;

    int weeks = (int) (remainder / (DateTimeConstants.MILLIS_PER_DAY * 7));
    remainder -= weeks * DateTimeConstants.MILLIS_PER_DAY * 7;

    int days = (int) (remainder / DateTimeConstants.MILLIS_PER_DAY);
    remainder -= days * DateTimeConstants.MILLIS_PER_DAY;

    int hours = (int) (remainder / DateTimeConstants.MILLIS_PER_HOUR);
    remainder -= hours * DateTimeConstants.MILLIS_PER_HOUR;

    int minutes = (int) (remainder / DateTimeConstants.MILLIS_PER_MINUTE);
    remainder -= minutes * DateTimeConstants.MILLIS_PER_MINUTE;

    int seconds = (int) (remainder / DateTimeConstants.MILLIS_PER_SECOND);
    remainder -= seconds * DateTimeConstants.MILLIS_PER_SECOND;

    int millis = (int) remainder;

    setPeriod(years, months, weeks, days, hours, minutes, seconds, millis);
}
