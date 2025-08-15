public char[] getTextBuffer()
{
    // Are we just using shared input buffer?
    if (_inputStart >= 0) return _inputBuffer;
    if (_resultArray != null)  return _resultArray;
    if (_resultString != null) {
        return (_resultArray = _resultString.toCharArray());
    }
    // Nope; but does it fit in just one segment?
    if (!_hasSegments) {
        // _currentSegment may be null if empty/resetWithEmpty was called
        if (_currentSegment == null) {
            return TextBuffer.NO_CHARS; // return empty array instead of null
        }
        return _currentSegment;
    }
    // Nope, need to have/create a non-segmented array and return it
    return contentsAsArray();
}
