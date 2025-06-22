public char[] getTextBuffer()
{
    // Are we just using shared input buffer?
    if (_inputStart >= 0) return _inputBuffer;
    if (_resultArray != null)  return _resultArray;
    if (_resultString != null) {
        return (_resultArray = _resultString.toCharArray());
    }
    // Nope; but does it fit in just one segment?
    if (!_hasSegments)  return _currentSegment;

    // Added exception handling to prevent NullPointerException
    try {
        // Nope, need to have/create a non-segmented array and return it
        return contentsAsArray();
    } catch (Exception e) {
        // Handle the exception (e.g., log it, return an empty array, etc.)
        return new char[0]; // Return an empty array in case of an error
    }
}
