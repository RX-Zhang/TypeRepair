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
    _inputPtr = 0; // reset pointer to 0 to adjust offset
    _inputEnd = end - start; // adjust effective buffer length to exclude start offset
    _currInputProcessed = start; // save the start offset to use in location calculations
    _currInputRow = 1; // assuming input starts at line 1
    _currInputRowStart = 0; // assuming first line starts at 0
    _bufferRecyclable = bufferRecyclable;
}
