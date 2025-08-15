public long adjustOffset(long instant, boolean earlierOrLater) {
    // evaluate instants a few hours before and after to detect overlaps
    long instantBefore = convertUTCToLocal(instant - 3 * DateTimeConstants.MILLIS_PER_HOUR);
    long instantAfter = convertUTCToLocal(instant + 3 * DateTimeConstants.MILLIS_PER_HOUR);
    if (instantBefore >= instantAfter) {
        // gap or normal case, no overlap detected
        return instant;
    }

    // determine the local time of the instant
    long local = convertUTCToLocal(instant);

    // find the transition times surrounding the overlap
    long overlapStart = instantBefore;
    long overlapEnd = instantAfter;

    // adjust the instant according to earlierOrLater flag
    if (earlierOrLater) {
        // want the later offset at overlap
        return convertLocalToUTC(local, false, overlapEnd);
    } else {
        // want the earlier offset at overlap
        return convertLocalToUTC(local, false, overlapStart);
    }
}
