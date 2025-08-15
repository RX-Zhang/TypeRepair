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

  if (lineNumber < 1) {
    return null;
  }

  int pos = 0;
  int startLine = 1;

  // If we've saved a previous offset and it's for a line less than the
  // one we're searching for, then start at that point.
  if (lineNumber >= lastLine) {
    pos = lastOffset;
    startLine = lastLine;
  }

  for (int n = startLine; n < lineNumber; n++) {
    int nextpos = js.indexOf('\n', pos);
    if (nextpos == -1) {
      // We have fewer lines than requested
      return null;
    }
    pos = nextpos + 1;
  }

  lastOffset = pos;
  lastLine = lineNumber;

  int endPos = js.indexOf('\n', pos);
  if (endPos == -1) {
    // Return remainder of code if not at EOF
    if (pos >= js.length()) {
      return null;
    }
    return js.substring(pos);
  } else {
    return js.substring(pos, endPos);
  }
}
