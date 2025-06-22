public StringBuffer format(Calendar calendar, StringBuffer buf) {
    if (!mTimeZoneForced && !calendar.getTimeZone().equals(mTimeZone)) {
        calendar = (Calendar) calendar.clone();
        calendar.setTimeZone(mTimeZone);
    } else if (mTimeZoneForced && !calendar.getTimeZone().equals(mTimeZone)) {
        calendar = (Calendar) calendar.clone();
        calendar.setTimeZone(mTimeZone);
    }
    return applyRules(calendar, buf);
}
