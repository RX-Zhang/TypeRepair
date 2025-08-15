public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    final int len = curr.length;
    int newLen;

    if (len >= MAX_SEGMENT_LEN) {
        newLen = len + 1;
    } else {
        newLen = len + (len >> 1);
        if (newLen > MAX_SEGMENT_LEN) {
            newLen = MAX_SEGMENT_LEN;
        }
    }

    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
