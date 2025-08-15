public Week(Date time, TimeZone zone) {
    // defer argument checking...
    Calendar calendar = Calendar.getInstance(zone, Locale.getDefault());
    calendar.setTime(time);
    this.week = (byte) calendar.get(Calendar.WEEK_OF_YEAR);
    this.year = (short) calendar.get(Calendar.YEAR);
    peg(calendar);
}
