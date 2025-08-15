public String format(Date date) {
    Calendar c = new GregorianCalendar(mTimeZone);
    c.setTime(date);
    int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
    // Adjusting for the case when the first week of the year is counted as week 1
    if (weekOfYear == 1 && c.get(Calendar.MONTH) == Calendar.DECEMBER) {
        weekOfYear = 53;
    }
    c.set(Calendar.WEEK_OF_YEAR, weekOfYear);
    return applyRules(c, new StringBuffer(mMaxLengthEstimate)).toString();
}
