public String format(Date date) {
    Calendar cal = Calendar.getInstance(mTimeZone, mLocale);
    cal.setTime(date);
    return applyRules(cal, new StringBuffer(mMaxLengthEstimate)).toString();
}
