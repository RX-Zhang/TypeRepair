public String format(Date date) {
    Calendar c = new GregorianCalendar(mTimeZone);
    c.setTime(date);

    // Adjust the week calculation based on the locale and the desired behavior for week numbers
    int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
    if (c.get(Calendar.MONTH) == Calendar.JANUARY && weekOfYear == 1 && c.get(Calendar.DAY_OF_MONTH) > 7) {
        // If it's January and the first week is after the 7th, it might belong to the last week of the previous year
        weekOfYear = c.getActualMaximum(Calendar.WEEK_OF_YEAR);
    }
    c.set(Calendar.WEEK_OF_YEAR, weekOfYear);

    return applyRules(c, new StringBuffer(mMaxLengthEstimate)).toString();
}
