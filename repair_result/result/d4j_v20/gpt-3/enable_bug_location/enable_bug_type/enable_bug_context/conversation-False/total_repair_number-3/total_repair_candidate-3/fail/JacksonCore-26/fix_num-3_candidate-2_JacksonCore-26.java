public void feedInput(byte[] buf, int start, int end) throws IOException
{
    // Must not have remaining input
    if (_inputPtr < _inputEnd) {
        _reportError("Still have %d undecoded bytes, should not call 'feedInput'", _inputEnd - _inputPtr);
    }
    if (end < start) {
        _reportError("Input end (%d) may not be before start (%d)", end, start);
    }
    // and shouldn't have been marked as end-of-input
    if (_endOfInput) {
        _reportError("Already closed, can not feed more input");
    }
    // Time to update pointers first
    // Fixing the logical error here by updating _currInputProcessed with the correct value
    _currInputProcessed += (_inputEnd - _inputPtr);

    // Also need to adjust row start, to work as if it extended into the past wrt new buffer
    _currInputRowStart = start;

    // And then update buffer settings
    _inputBuffer = buf;
    _inputPtr = start;
    _inputEnd = end;
    _origBufferLen = end - start;
}
