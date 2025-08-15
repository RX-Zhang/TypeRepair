public TimeSeries createCopy(RegularTimePeriod start, RegularTimePeriod end)
    throws CloneNotSupportedException {

    if (start == null) {
        throw new IllegalArgumentException("Null 'start' argument.");
    }
    if (end == null) {
        throw new IllegalArgumentException("Null 'end' argument.");
    }
    if (start.compareTo(end) > 0) {
        throw new IllegalArgumentException(
                "Requires start on or before end.");
    }
    boolean emptyRange = false;
    int startIndex = getIndex(start);
    if (startIndex < 0) {
        startIndex = -(startIndex + 1);
        // if startIndex is equal to data size, meaning start is after last data item
        if (startIndex == this.data.size()) {
            emptyRange = true;  // start is after last data item
        }
    }
    int endIndex = getIndex(end);
    if (endIndex < 0) {
        endIndex = -(endIndex + 1) - 1; // last item before end period
    }
    // Correct for case where endIndex is less than startIndex or negative
    if (endIndex < startIndex) {
        emptyRange = true;
    }
    if (emptyRange) {
        TimeSeries copy = (TimeSeries) super.clone();
        copy.data = new java.util.ArrayList();
        return copy;
    }
    else {
        return createCopy(startIndex, endIndex);
    }

}
