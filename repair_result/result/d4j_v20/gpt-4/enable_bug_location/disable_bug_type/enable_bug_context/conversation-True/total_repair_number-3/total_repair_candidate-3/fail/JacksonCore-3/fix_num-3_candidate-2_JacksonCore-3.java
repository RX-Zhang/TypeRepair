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
    _currInputProcessed = start; // Correcting the logical error by initializing _currInputProcessed with 'start'
    _currInputRowStart = start;  // This also needs to be set correctly based on the provided start
    _bufferRecyclable = bufferRecyclable;
}
