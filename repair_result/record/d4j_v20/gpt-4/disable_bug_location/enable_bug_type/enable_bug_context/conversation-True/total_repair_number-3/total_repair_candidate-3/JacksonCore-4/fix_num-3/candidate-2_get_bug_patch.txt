public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    final int len = curr.length;
    // Let's grow by 50% by default
    int newLen = len + (len >> 1);
    // If current length is at or above MAX_SEGMENT_LEN, grow slowly by 25%
    if (len >= MAX_SEGMENT_LEN) {
        newLen = len + (len >> 2);
        if (newLen <= len) {
            newLen = len + 1;
        }
    }
    if (newLen > MAX_SEGMENT_LEN) {
        newLen = MAX_SEGMENT_LEN;
    }
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
