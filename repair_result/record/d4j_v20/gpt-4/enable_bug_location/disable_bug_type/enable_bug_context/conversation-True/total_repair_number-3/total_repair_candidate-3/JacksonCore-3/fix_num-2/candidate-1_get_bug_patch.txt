public UTF8StreamJsonParser(IOContext ctxt, int features, InputStream in,
        ObjectCodec codec, BytesToNameCanonicalizer sym,
        byte[] inputBuffer, int start, int end,
        boolean bufferRecyclable)
{
    super(ctxt, features);
    _inputStream = in;
    _objectCodec = codec;
    _symbols = sym;
    _inputBuffer = inputBuffer;
    _inputPtr = start;
    _inputEnd = end;
    // If we have offset, need to omit that from byte offset, so:
    _currInputProcessed = start; // Correctly initialize the processed input
    _currInputRowStart = start;  // Set the current row start to the input pointer
    _bufferRecyclable = bufferRecyclable;
}
