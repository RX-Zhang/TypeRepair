public StringBuffer format(Calendar calendar, StringBuffer buf) {
    if (mTimeZoneForced) {
        calendar = (Calendar) calendar.clone();
        calendar.setTimeZone(mTimeZone);
        // Adjust the time to the new time zone to reflect correctly
        calendar.setTimeInMillis(calendar.getTimeInMillis());
    }
    return applyRules(calendar, buf);
}
