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
    _inputPtr = 0;
    _inputEnd = end - start;
    _currInputProcessed = start;
    _currInputRowStart = 0;
    // If we have offset, need to omit that from byte offset, so:
    _bufferRecyclable = bufferRecyclable;
}
