public String format(Date date) {
    Calendar c = Calendar.getInstance(mTimeZone, mLocale);
    c.setTime(date);
    return applyRules(c, new StringBuffer(mMaxLengthEstimate)).toString();
}
