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
    // Correct the byte offset calculation to account for the start offset
    _currInputProcessed = start; // Initialize the current input processed to start
    _currInputRowStart = start; // Initialize the current input row start to start
    _bufferRecyclable = bufferRecyclable;
}
