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
    _currInputProcessed = 0; // Initialize this to track processed input correctly
    _currInputRowStart = 0; // Initialize this to track row start correctly
    _bufferRecyclable = bufferRecyclable;
}
