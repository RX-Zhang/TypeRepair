public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    final int len = curr.length;
    int newLen;
    if (len == MAX_SEGMENT_LEN) {
        newLen = MAX_SEGMENT_LEN + 1;
    } else if (len > MAX_SEGMENT_LEN) {
        // Already over maximum, just increase by 1
        newLen = len + 1;
    } else {
        // Grow by 50%, but not beyond MAX_SEGMENT_LEN
        newLen = len + (len >> 1);
        if (newLen > MAX_SEGMENT_LEN) {
            newLen = MAX_SEGMENT_LEN;
        }
    }
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
