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
    _inputPtr = 0; // always reset input pointer to 0, because offset is accounted separately
    _inputEnd = end - start; // adjust _inputEnd to reflect actual buffer length after offset
    // Adjust offset tracking to reflect initial start offset in input source
    _currInputProcessed = start;
    _bufferRecyclable = bufferRecyclable;
}
