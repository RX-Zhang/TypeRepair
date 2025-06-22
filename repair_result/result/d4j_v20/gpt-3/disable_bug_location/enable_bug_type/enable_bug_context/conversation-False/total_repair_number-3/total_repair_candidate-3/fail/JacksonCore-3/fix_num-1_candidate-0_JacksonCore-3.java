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
    // Store the original input buffer start offset so that byte offsets are computed correctly
    _type = TYPE_STREAM; // ensure type is set if required (not shown in original snippet)
    _bufferRecyclable = bufferRecyclable;

    // Initialize offset tracking fields appropriately; fix logical error
    _currInputProcessed = -start; // Adjust to subtract offset for correct location reporting
    _currInputRowStart = 0;
    _currInputRow = 1;
}
