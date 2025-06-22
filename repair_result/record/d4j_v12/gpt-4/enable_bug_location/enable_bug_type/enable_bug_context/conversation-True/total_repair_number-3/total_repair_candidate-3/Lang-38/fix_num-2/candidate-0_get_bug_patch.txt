public StringBuffer format(Calendar calendar, StringBuffer buf) {
    if (getTimeZoneOverridesCalendar()) {
        calendar = (Calendar) calendar.clone();
        calendar.setTimeZone(mTimeZone);
    }
    return applyRules(calendar, buf);
}
