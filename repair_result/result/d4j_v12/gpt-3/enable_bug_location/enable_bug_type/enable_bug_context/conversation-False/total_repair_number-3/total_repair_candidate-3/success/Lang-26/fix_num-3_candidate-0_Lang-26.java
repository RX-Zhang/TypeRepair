public String format(Date date) {
    Calendar c = new GregorianCalendar(mTimeZone);
    c.setTime(date);
    // Adjust the week of year to match the ISO standard
    c.setFirstDayOfWeek(Calendar.MONDAY);
    c.setMinimalDaysInFirstWeek(4);
    return applyRules(c, new StringBuffer(mMaxLengthEstimate)).toString();
}
