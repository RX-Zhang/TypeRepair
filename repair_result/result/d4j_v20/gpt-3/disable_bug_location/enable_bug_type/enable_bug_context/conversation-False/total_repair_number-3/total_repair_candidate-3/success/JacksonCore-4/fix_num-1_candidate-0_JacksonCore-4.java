public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    // Let's grow by 50% by default
    final int len = curr.length;
    int newLen;
    if (len == MAX_SEGMENT_LEN) {
        // Already at max, so increase length by 1 (allowing minimal growth)
        newLen = MAX_SEGMENT_LEN + 1;
    } else if (len > MAX_SEGMENT_LEN) {
        // If length somehow exceeded MAX, increase by 25%
        newLen = Math.min(curr.length + (curr.length >> 2), Integer.MAX_VALUE);
    } else {
        // Under max: grow by 50%, but do not exceed MAX_SEGMENT_LEN
        newLen = Math.min(MAX_SEGMENT_LEN, len + (len >> 1));
    }
    // Ensure newLen is greater than len to actually expand buffer
    if (newLen <= len) {
        newLen = len + 1;
    }
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
