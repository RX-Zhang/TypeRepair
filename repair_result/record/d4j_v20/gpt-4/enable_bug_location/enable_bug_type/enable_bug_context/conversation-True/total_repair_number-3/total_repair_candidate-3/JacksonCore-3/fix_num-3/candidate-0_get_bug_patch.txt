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
    _inputPtr = 0;  // corrected to start at 0 internally
    _currInputProcessed = start; // keep track of input offset
    _inputEnd = end;
    _bufferRecyclable = bufferRecyclable;
}
