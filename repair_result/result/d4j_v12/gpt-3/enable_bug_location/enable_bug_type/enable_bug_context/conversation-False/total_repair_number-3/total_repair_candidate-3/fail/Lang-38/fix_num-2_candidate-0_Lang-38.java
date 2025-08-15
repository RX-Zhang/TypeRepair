public StringBuffer format(Calendar calendar, StringBuffer buf) {
    if (mTimeZoneForced) {
        calendar = (Calendar) calendar.clone();
        calendar.setTimeZone(mTimeZone);
        // Also adjust the time to correspond correctly to the new timezone
        long time = calendar.getTimeInMillis();
        calendar.setTimeInMillis(time);
    }
    return applyRules(calendar, buf);
}
