public char[] expandCurrentSegment()
{
    // Delegate to the private expand method which manages segments properly
    int minNewSegmentSize = _currentSegment.length + (_currentSegment.length >> 1);
    if (minNewSegmentSize < MIN_SEGMENT_LEN) {
        minNewSegmentSize = MIN_SEGMENT_LEN;
    } else if (minNewSegmentSize > MAX_SEGMENT_LEN) {
        minNewSegmentSize = MAX_SEGMENT_LEN;
    }
    expand(minNewSegmentSize);
    return _currentSegment;
}
