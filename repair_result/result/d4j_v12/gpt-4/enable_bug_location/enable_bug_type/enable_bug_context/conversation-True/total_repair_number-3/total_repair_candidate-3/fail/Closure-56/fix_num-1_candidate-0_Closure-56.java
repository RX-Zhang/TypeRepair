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

  if (lineNumber > getNumLines()) {
    return null;
  }

  int start = getLineOffset(lineNumber);
  int end;
  if (lineNumber == getNumLines()) {
    end = js.length();
  } else {
    end = getLineOffset(lineNumber + 1) - 1; // exclude the newline char
  }

  // Remember this offset for the next search we do.
  lastOffset = start;
  lastLine = lineNumber;

  if (start >= js.length()) {
    return null;
  }

  return js.substring(start, end);
}
