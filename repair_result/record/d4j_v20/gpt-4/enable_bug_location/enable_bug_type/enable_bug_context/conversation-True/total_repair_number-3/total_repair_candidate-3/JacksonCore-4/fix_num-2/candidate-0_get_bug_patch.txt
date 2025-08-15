public char[] expandCurrentSegment()
{
    int len = _currentSegment.length;
    if (len >= MAX_SEGMENT_LEN) {
        expand(len + 1);
    } else {
        expand(len + (len >> 1));
    }
    return _currentSegment;
}
