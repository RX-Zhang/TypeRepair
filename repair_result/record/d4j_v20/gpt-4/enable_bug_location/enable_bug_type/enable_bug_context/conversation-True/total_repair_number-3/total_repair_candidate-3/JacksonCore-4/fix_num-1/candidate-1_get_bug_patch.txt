public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    final int len = curr.length;
    // Use the existing expand method with minNewSegmentSize to ensure proper growth
    int newLen = len + (len >> 1);
    if (newLen > MAX_SEGMENT_LEN) {
        if (len == MAX_SEGMENT_LEN) {
            newLen = MAX_SEGMENT_LEN + 1;
        } else {
            newLen = MAX_SEGMENT_LEN;
        }
    }
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
