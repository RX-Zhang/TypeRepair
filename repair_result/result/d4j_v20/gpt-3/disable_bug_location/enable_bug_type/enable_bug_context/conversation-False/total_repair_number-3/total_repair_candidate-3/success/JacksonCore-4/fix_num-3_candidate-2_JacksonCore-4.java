public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    final int len = curr.length;
    // Increase by 50% unless hitting MAX_SEGMENT_LEN, then increase more slowly by 25%
    int newLen;
    if (len >= MAX_SEGMENT_LEN) {
        // If length is already at or above max, increase by 25%
        newLen = Math.min(MAX_SEGMENT_LEN + (MAX_SEGMENT_LEN >> 2), Integer.MAX_VALUE);
        // ensure newLen is larger than len to allow expansion
        if (newLen <= len) {
            newLen = len + 1;
        }
    } else {
        // normal 50% increase, but don't exceed MAX_SEGMENT_LEN
        newLen = len + (len >> 1);
        if (newLen > MAX_SEGMENT_LEN) {
            newLen = MAX_SEGMENT_LEN;
        }
    }
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
