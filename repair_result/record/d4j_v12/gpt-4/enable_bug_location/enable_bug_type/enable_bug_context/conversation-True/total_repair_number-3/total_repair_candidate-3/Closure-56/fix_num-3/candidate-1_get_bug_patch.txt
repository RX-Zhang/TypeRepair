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

  int startLine = 1;
  int pos = 0;

  // If we've saved a previous offset and it's for a line less than the
  // one we're searching for, then start at that point.
  if (lineNumber >= lastLine) {
    pos = lastOffset;
    startLine = lastLine;
  }

  // Find the offset for the requested line by walking forward from pos
  for (int n = startLine; n < lineNumber; n++) {
    int nextpos = js.indexOf('\n', pos);
    if (nextpos == -1) {
      // No more lines, but asked for a line beyond that
      return null;
    }
    pos = nextpos + 1;
  }

  // Remember this offset for the next search we do.
  lastOffset = pos;
  lastLine = lineNumber;

  int nextNewLinePos = js.indexOf('\n', pos);
  if (nextNewLinePos == -1) {
    // If no next new line, return substring from pos to end of string
    return js.substring(pos);
  } else {
    // Return substring between pos and next new line
    return js.substring(pos, nextNewLinePos);
  }
}
