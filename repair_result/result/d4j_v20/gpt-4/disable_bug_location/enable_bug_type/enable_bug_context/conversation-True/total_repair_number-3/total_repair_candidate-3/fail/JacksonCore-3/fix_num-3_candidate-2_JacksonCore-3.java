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
    _inputPtr = 0; // Reset input pointer to 0, offset handled separately
    _inputEnd = end - start; // Adjust end relative to the new start
    _currInputProcessed = start; // Track bytes skipped at the start for location calculations
    _bufferRecyclable = bufferRecyclable;
}
