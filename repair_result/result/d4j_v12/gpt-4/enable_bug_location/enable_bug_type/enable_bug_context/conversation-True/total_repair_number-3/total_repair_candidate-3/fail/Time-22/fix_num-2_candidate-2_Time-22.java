protected BasePeriod(long duration) {
    setPeriod(0, 0, 0, 0, (int) (duration / DateTimeConstants.MILLIS_PER_HOUR),
              (int) ((duration / DateTimeConstants.MILLIS_PER_MINUTE) % 60),
              (int) ((duration / DateTimeConstants.MILLIS_PER_SECOND) % 60),
              (int) (duration % 1000));
}
