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
    _inputPtr = 0; // reset ptr to zero as offset is accounted for
    _inputEnd = end - start; // adjust length for offset
    _currInputProcessed = start; // track total bytes processed including offset
    _bufferRecyclable = bufferRecyclable;
}
