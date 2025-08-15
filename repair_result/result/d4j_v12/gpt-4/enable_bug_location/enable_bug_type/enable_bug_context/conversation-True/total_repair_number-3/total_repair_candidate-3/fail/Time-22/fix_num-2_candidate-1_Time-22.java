protected BasePeriod(long duration) {
    setPeriod((int) 0, (int) 0, (int) 0, (int) (duration / DateTimeConstants.MILLIS_PER_DAY),
              (int) ((duration % DateTimeConstants.MILLIS_PER_DAY) / DateTimeConstants.MILLIS_PER_HOUR),
              (int) ((duration % DateTimeConstants.MILLIS_PER_HOUR) / DateTimeConstants.MILLIS_PER_MINUTE),
              (int) ((duration % DateTimeConstants.MILLIS_PER_MINUTE) / DateTimeConstants.MILLIS_PER_SECOND),
              (int) (duration % DateTimeConstants.MILLIS_PER_SECOND));
}
