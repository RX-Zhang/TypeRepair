public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    // Let's grow by 50% by default
    final int len = curr.length;
    int newLen;
    if (len >= MAX_SEGMENT_LEN) {
        // If length is already at or beyond max, grow slowly by 25%
        newLen = len + (len >> 2);
    } else {
        // Otherwise grow by 50%, but do not exceed MAX_SEGMENT_LEN
        newLen = len + (len >> 1);
        if (newLen > MAX_SEGMENT_LEN) {
            newLen = MAX_SEGMENT_LEN;
        }
    }
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
