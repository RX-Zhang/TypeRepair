protected BasePeriod(long duration) {
    // Use standard period type and UTC chronology to avoid time zone effects
    this(duration, null, ISOChronology.getInstanceUTC());
    // bug [3264409]
}
