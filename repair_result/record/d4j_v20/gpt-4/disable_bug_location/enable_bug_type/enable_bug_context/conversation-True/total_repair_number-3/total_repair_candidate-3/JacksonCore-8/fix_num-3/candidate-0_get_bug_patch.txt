public char[] getTextBuffer()
{
    // Are we just using shared input buffer?
    if (_inputStart >= 0) {
        if (_inputBuffer == null) {
            // Defensive: _inputBuffer should not be null if _inputStart >= 0
            return NO_CHARS;
        }
        return _inputBuffer;
    }
    if (_resultArray != null)  return _resultArray;
    if (_resultString != null) {
        return (_resultArray = _resultString.toCharArray());
    }
    // Nope; but does it fit in just one segment?
    if (!_hasSegments)  {
        if (_currentSegment == null) {
            // Defensive: if no segments and current segment null, return empty array
            return NO_CHARS;
        }
        return _currentSegment;
    }
    // Nope, need to have/create a non-segmented array and return it
    return contentsAsArray();
}
