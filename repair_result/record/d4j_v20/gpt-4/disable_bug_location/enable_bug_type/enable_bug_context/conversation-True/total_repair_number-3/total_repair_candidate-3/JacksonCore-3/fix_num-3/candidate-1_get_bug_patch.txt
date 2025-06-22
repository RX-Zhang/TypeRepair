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
    _inputPtr = 0; // reset to 0 to reflect start of buffer relative to input
    _inputEnd = end;
    // Adjust _currInputProcessed to account for offset 'start' being trimmed
    _currInputProcessed = start;
    // If we have offset, need to omit that from byte offset, so:
    _bufferRecyclable = bufferRecyclable;
}
