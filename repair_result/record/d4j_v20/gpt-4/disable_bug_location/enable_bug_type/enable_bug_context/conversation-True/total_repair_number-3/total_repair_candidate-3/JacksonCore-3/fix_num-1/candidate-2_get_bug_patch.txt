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
    _inputPtr = 0;  // Reset pointer to 0 since offset should not affect location reporting
    _inputEnd = end;
    // If we have offset, need to add that from processed bytes, not from pointer start
    _currInputProcessed = start;  // Store offset here for correct byte offset calculation

    _bufferRecyclable = bufferRecyclable;
}
