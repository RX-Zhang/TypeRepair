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
    _bufferRecyclable = bufferRecyclable;

    // Need to adjust starting input offset so locations compute correctly
    // _currInputProcessed tracks the starting input offset: set to starting offset so locations subtract properly
    _currInputProcessed = -start;
}
