public StringBuffer format(Calendar calendar, StringBuffer buf) {
    if (mTimeZoneForced) {
        calendar = (Calendar) calendar.clone();
        // Adjust the time to the new timezone to reflect the correct instant
        long timeInMillis = calendar.getTimeInMillis();
        calendar.setTimeZone(mTimeZone);
        calendar.setTimeInMillis(timeInMillis);
    }
    return applyRules(calendar, buf);
}
