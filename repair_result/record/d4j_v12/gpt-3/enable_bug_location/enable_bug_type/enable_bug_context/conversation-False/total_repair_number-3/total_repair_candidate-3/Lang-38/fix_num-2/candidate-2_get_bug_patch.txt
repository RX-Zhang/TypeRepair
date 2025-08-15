public StringBuffer format(Calendar calendar, StringBuffer buf) {
    if (mTimeZoneForced) {
        calendar = (Calendar) calendar.clone();
        Date originalDate = calendar.getTime();
        calendar.setTimeZone(mTimeZone);
        calendar.setTime(originalDate);
    }
    return applyRules(calendar, buf);
}
