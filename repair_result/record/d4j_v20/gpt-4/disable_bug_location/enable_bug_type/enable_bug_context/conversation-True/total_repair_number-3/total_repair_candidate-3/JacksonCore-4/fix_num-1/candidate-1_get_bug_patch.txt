public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    final int len = curr.length;
    int newLen;

    if (len >= MAX_SEGMENT_LEN) {
        // Already at or above max, increase by 1 to force expansion beyond max
        newLen = len + 1;
    } else {
        // Grow by 50%, but do not exceed MAX_SEGMENT_LEN
        newLen = len + (len >> 1);
        if (newLen > MAX_SEGMENT_LEN) {
            newLen = MAX_SEGMENT_LEN;
        }
    }
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
