public char[] expandCurrentSegment()
{
    final int len = _currentSegment.length;
    int newLen = len + (len >> 1);
    if (newLen < TextBuffer.MIN_SEGMENT_LEN) {
        newLen = TextBuffer.MIN_SEGMENT_LEN;
    } else if (newLen > TextBuffer.MAX_SEGMENT_LEN) {
        newLen = TextBuffer.MAX_SEGMENT_LEN;
    }
    return (_currentSegment = Arrays.copyOf(_currentSegment, newLen));
}
