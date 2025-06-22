public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    // Let's grow by 50% by default
    final int len = curr.length;
    // Ensure we are not exceeding the maximum length
    if (len >= MAX_SEGMENT_LEN) {
        return curr; // No expansion needed if already at max length
    }
    // Grow by 50%, but ensure it does not exceed the maximum
    int newLen = Math.min(MAX_SEGMENT_LEN, len + (len >> 1));
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
