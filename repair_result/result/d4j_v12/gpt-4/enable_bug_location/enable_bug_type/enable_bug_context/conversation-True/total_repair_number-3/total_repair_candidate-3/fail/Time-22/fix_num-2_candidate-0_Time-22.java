protected BasePeriod(long duration) {
    setPeriod((ReadablePeriod) null);  // initialize all fields to zero
    // bug [3264409]
}
