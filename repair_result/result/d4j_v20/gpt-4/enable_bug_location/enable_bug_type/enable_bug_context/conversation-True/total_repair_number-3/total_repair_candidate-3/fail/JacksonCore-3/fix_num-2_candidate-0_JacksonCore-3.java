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
    _inputPtr = 0; // reset pointer to 0 so offsets are relative to this buffer start
    _inputEnd = end - start;
    _currInputProcessed = start; // set processed bytes count to offset start
    _currInputRowStart = 0; // assume row start as offset 0 relative to current buffer start
    _bufferRecyclable = bufferRecyclable;
}
