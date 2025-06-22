public char[] expandCurrentSegment()
{
    if (_currentSize > 0) {
        expand(_currentSize + 1);
        return _currentSegment;
    }
    final char[] curr = _currentSegment;
    final int len = curr.length;
    if (len >= MAX_SEGMENT_LEN) {
        throw new IllegalStateException("Cannot expand segment beyond max length of " + MAX_SEGMENT_LEN);
    }
    int newLen = Math.min(MAX_SEGMENT_LEN, len + (len >> 1));
    return (_currentSegment = Arrays.copyOf(curr, newLen));
}
