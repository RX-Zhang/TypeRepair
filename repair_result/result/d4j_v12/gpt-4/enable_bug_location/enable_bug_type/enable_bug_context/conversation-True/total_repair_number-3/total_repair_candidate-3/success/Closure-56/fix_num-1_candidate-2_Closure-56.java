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

  if (lastLine > lineNumber) {
    // Reset cached offsets if we try to access an earlier line.
    lastOffset = 0;
    lastLine = 1;
  }

  int pos = lastOffset;
  int startLine = lastLine;

  for (int n = startLine; n < lineNumber; n++) {
    int nextpos = js.indexOf('\n', pos);
    if (nextpos == -1) {
      // Requested line number is beyond the total number of lines.
      return null;
    }
    pos = nextpos + 1;
  }

  lastOffset = pos;
  lastLine = lineNumber;

  int nextNewLine = js.indexOf('\n', pos);
  if (nextNewLine == -1) {
    if (pos >= js.length()) {
      return null;
    } else {
      return js.substring(pos);
    }
  } else {
    return js.substring(pos, nextNewLine);
  }
}
