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
    _inputPtr = 0;              // reset pointer to zero to align offsets correctly
    _inputStart = start;        // track start offset for correct byte offset calculation
    _inputEnd = end;
    _bufferRecyclable = bufferRecyclable;
}
