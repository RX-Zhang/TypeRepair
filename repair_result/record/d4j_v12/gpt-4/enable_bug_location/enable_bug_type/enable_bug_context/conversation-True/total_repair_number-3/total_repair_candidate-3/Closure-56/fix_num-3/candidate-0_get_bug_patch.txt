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

  int numLines = getNumLines();

  if (lineNumber < 1 || lineNumber > numLines) {
    return null;
  }

  int startOffset = getLineOffset(lineNumber);
  int endOffset = (lineNumber == numLines) ? js.length() : getLineOffset(lineNumber + 1) - 1;

  if (startOffset > js.length() || startOffset < 0) {
    return null;
  }

  return js.substring(startOffset, Math.min(endOffset, js.length()));
}
