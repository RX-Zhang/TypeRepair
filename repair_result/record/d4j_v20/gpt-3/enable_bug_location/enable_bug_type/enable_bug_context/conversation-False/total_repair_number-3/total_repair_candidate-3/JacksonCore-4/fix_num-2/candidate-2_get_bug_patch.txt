public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    // Let's grow by 50% by default
    final int len = curr.length;
    int newLen;
    if (len == MAX_SEGMENT_LEN) {
        // already at max, grow by 1
        newLen = MAX_SEGMENT_LEN + 1;
    } else if (len > MAX_SEGMENT_LEN) {
        // above max, slow to increase by 25%
        newLen = len + (len >> 2);
    } else {
        // below max, grow by 50%, but do not exceed MAX_SEGMENT_LEN
        newLen = len + (len >> 1);
        if (newLen > MAX_SEGMENT_LEN) {
            newLen = MAX_SEGMENT_LEN;
        }
    }
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
