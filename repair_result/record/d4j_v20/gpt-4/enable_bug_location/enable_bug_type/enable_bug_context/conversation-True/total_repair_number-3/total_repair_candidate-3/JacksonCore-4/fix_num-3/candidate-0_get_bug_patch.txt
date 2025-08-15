public char[] expandCurrentSegment()
{
    final char[] curr = _currentSegment;
    final int oldLen = curr.length;
    int newLen;

    if (oldLen < MAX_SEGMENT_LEN) {
        // grow by 50%, but limited to MAX_SEGMENT_LEN
        newLen = oldLen + (oldLen >> 1);
        if (newLen > MAX_SEGMENT_LEN) {
            newLen = MAX_SEGMENT_LEN;
        }
    } else {
        // at max length, grow by 1 only to force fail
        newLen = oldLen + 1;
    }
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
