public char[] expandCurrentSegment()
{
    if (_currentSegment == null) {
        _currentSegment = carr(MIN_SEGMENT_LEN);
        _currentSize = 0;
        return _currentSegment;
    }
    int len = _currentSegment.length;
    if (len >= MAX_SEGMENT_LEN) {
        return _currentSegment;
    }
    int newLen = len + (len >> 1);
    if (newLen > MAX_SEGMENT_LEN) {
        newLen = MAX_SEGMENT_LEN;
    }
    char[] expanded = carr(newLen);
    System.arraycopy(_currentSegment, 0, expanded, 0, _currentSize);
    _currentSegment = expanded;
    return _currentSegment;
}
