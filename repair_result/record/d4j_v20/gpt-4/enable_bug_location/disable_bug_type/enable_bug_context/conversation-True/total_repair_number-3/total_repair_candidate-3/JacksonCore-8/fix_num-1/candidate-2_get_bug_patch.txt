public char[] getTextBuffer()
{
    // Are we just using shared input buffer?
    if (_inputStart >= 0) {
        return (_inputLen > 0) ? _inputBuffer : NO_CHARS;
    }
    if (_resultArray != null)  return _resultArray;
    if (_resultString != null) {
        return (_resultArray = _resultString.toCharArray());
    }
    // Nope; but does it fit in just one segment?
    if (!_hasSegments) {
        return (_currentSize > 0) ? _currentSegment : NO_CHARS;
    }
    // Nope, need to have/create a non-segmented array and return it
    return contentsAsArray();
}
