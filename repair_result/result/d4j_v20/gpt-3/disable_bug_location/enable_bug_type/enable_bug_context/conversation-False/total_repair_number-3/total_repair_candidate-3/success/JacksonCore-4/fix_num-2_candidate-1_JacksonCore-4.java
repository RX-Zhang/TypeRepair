public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    // Let's grow by 50% by default
    final int len = curr.length;
    int newLen;
    if (len >= MAX_SEGMENT_LEN) {
        // If already at or above max, increase by a small fixed amount to allow further expansion
        newLen = len + 1;
    } else {
        // Grow by 50% but do not exceed MAX_SEGMENT_LEN
        newLen = Math.min(MAX_SEGMENT_LEN, len + (len >> 1));
        if (newLen <= len) {
            // Defensive: ensure newLen is always greater than current length
            newLen = len + 1;
        }
    }
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
