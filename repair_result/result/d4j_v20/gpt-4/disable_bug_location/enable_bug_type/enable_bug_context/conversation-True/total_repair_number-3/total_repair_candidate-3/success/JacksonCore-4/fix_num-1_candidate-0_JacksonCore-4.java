public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    // Let's grow by 50% by default
    final int len = curr.length;
    // if at max, grow slightly (by 1) to allow a minimal expansion
    int newLen;
    if (len >= MAX_SEGMENT_LEN) {
        newLen = len + 1;
    } else {
        newLen = Math.min(MAX_SEGMENT_LEN, len + (len >> 1));
    }
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
