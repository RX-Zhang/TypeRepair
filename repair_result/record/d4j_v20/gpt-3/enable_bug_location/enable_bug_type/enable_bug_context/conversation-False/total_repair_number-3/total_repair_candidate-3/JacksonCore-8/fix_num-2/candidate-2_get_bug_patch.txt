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
        if (_currentSegment != null) {
            return _currentSegment; // Ensure currentSegment is not null
        } else {
            // Handle potential null case by returning an empty array
            return new char[0];
        }
    }
    // Nope, need to have/create a non-segmented array and return it
    return contentsAsArray();
}
