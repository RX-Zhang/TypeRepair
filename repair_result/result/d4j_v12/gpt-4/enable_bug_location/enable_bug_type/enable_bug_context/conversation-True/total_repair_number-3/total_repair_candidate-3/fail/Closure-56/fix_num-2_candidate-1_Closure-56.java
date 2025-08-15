public String getLine(int lineNumber) {
  String js = "";
  try {
    js = getCode();
  } catch (IOException e) {
    return null;
  }

  if (lineNumber < 1) {
    return null;
  }

  int numLines = getNumLines();
  if (lineNumber > numLines) {
    return null;
  }

  int startOffset = getLineOffset(lineNumber);
  int endOffset;
  if (lineNumber == numLines) {
    endOffset = js.length();
  } else {
    endOffset = getLineOffset(lineNumber + 1) - 1; // exclude the newline char
  }

  // Remember this offset for the next search we do.
  lastOffset = startOffset;
  lastLine = lineNumber;

  if (startOffset >= js.length()) {
    return null;
  }

  return js.substring(startOffset, Math.min(endOffset, js.length()));
}
