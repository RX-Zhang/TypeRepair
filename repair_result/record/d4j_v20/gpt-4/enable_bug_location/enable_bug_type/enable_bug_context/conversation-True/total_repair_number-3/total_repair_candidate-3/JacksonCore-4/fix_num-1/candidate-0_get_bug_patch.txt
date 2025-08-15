public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    final int len = curr.length;
    // Grow by 50% by default, but never less than MIN_SEGMENT_LEN
    int newLen = len + (len >> 1);
    if (newLen > MAX_SEGMENT_LEN) {
        newLen = MAX_SEGMENT_LEN;
    }
    if (newLen <= len) {
        // Normally shouldn't happen, but protect against it:
        newLen = len + 1;
    }
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
