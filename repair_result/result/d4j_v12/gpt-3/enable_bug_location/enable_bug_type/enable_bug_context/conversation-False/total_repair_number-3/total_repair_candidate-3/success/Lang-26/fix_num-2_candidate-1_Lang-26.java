public String format(Date date) {
    Calendar c = new GregorianCalendar(mTimeZone);
    c.setTime(date);
    
    // Correcting the logical error by adjusting the week calculation
    c.setFirstDayOfWeek(Calendar.MONDAY); // Set first day of the week to Monday
    c.setMinimalDaysInFirstWeek(4); // Set minimal days in the first week to 4 for ISO-8601 compliance
    
    return applyRules(c, new StringBuffer(mMaxLengthEstimate)).toString();
}
