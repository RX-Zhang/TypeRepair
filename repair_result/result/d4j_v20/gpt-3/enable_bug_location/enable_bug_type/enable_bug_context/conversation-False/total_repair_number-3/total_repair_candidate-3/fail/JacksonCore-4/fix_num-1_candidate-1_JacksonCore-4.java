public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    // Let's grow by 50% by default
    final int len = curr.length;
    // but if at max length, grow by 1 more (to allow growth)
    int newLen;
    if (len == MAX_SEGMENT_LEN) {
        newLen = MAX_SEGMENT_LEN + 1;
    } else {
        newLen = len + (len >> 1);
        if (newLen > MAX_SEGMENT_LEN) {
            newLen = MAX_SEGMENT_LEN;
        }
    }
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
