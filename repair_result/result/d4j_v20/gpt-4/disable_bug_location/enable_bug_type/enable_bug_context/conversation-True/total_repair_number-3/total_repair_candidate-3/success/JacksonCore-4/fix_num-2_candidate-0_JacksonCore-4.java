public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    final int len = curr.length;
    int newLen;
    if (len >= MAX_SEGMENT_LEN) {
        // Can't grow beyond MAX_SEGMENT_LEN, so grow by 1 if possible
        newLen = len + 1;
    } else {
        // Grow by 50%, but do not exceed MAX_SEGMENT_LEN
        newLen = Math.min(MAX_SEGMENT_LEN, len + (len >> 1));
    }
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
