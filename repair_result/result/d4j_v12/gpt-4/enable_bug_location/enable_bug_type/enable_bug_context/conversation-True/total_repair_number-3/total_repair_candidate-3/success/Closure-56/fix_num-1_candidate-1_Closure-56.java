public String getLine(int lineNumber) {
  String js = "";
  try {
    // NOTE(nicksantos): Right now, this is optimized for few warnings.
    // This is probably the right trade-off, but will be slow if there
    // are lots of warnings in one file.
    js = getCode();
  } catch (IOException e) {
    return null;
  }

  int totalLines = getNumLines();
  if (lineNumber < 1 || lineNumber > totalLines) {
    return null;
  }
  
  int startOffset = getLineOffset(lineNumber);
  int endOffset;
  if (lineNumber == totalLines) {
    endOffset = js.length();
  } else {
    endOffset = getLineOffset(lineNumber + 1);
  }

  // Remember this offset for potential future optimizations.
  lastOffset = startOffset;
  lastLine = lineNumber;

  return js.substring(startOffset, endOffset).replaceAll("\r?\n$", "");
}
