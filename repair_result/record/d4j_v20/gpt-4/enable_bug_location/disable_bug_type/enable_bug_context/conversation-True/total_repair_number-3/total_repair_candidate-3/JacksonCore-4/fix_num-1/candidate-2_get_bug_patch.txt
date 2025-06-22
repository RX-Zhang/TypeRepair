public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    // Let's grow by 50% by default
    final int len = curr.length;
    // if current length is already at maximum, we should not increase it
    if (len >= MAX_SEGMENT_LEN) {
        return curr; // Return current segment if already at max size
    }
    // Calculate new length, limited to MAX_SEGMENT_LEN
    int newLen = Math.min(MAX_SEGMENT_LEN, len + (len >> 1));
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
