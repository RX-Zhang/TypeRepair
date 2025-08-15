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

    // Adjust offset for current input pointer so that byte offset is correct
    // This ensures getTokenLocation() will report offsets starting at 0
    _currInputProcessed = -start;
}
