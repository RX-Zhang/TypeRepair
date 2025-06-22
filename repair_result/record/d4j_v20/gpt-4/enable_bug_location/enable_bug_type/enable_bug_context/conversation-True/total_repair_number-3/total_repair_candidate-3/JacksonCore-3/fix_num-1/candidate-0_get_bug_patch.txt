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
    _bufferRecyclable = bufferRecyclable;

    // Adjust offset tracking variables based on start offset:
    _currInputProcessed = start;  // track total bytes consumed before current buffer
    _currInputRow = 1;            // line number starts at 1
    _currInputRowStart = start;   // start offset of current line (buffer start offset)
}
