public long adjustOffset(long instant, boolean earlierOrLater) {
    // a bit messy, but will work in all non-pathological cases

    // evaluate 3 hours before and after to work out if anything is happening
    long instantBefore = convertUTCToLocal(instant - 3 * DateTimeConstants.MILLIS_PER_HOUR);
    long instantAfter = convertUTCToLocal(instant + 3 * DateTimeConstants.MILLIS_PER_HOUR);
    if (instantBefore == instantAfter) {
        return instant;  // not an overlap (less than is a gap, equal is normal case)
    }

    // work out range of instants that have duplicate local times
    long local = convertUTCToLocal(instant);

    // Fix the logic here: earlierOrLater means choose earlier or later offset.
    // earlierOrLater == true means we want the earlier offset -> use instantBefore
    // earlierOrLater == false means we want the later offset -> use instantAfter
    // So reverse the ternary condition accordingly.
    return convertLocalToUTC(local, false, earlierOrLater ? instantBefore : instantAfter);
}
