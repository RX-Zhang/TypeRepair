@Override public void skipValue() throws IOException {
  if (stackSize == 0) {
    return;
  }
  if (peek() == JsonToken.NAME) {
    nextName();
    if (stackSize - 2 >= 0 && stackSize - 2 < pathNames.length) {
      pathNames[stackSize - 2] = "null";
    }
  } else {
    popStack();
    if (stackSize - 1 >= 0 && stackSize - 1 < pathNames.length) {
      pathNames[stackSize - 1] = "null";
    }
  }
  if (stackSize - 1 >= 0 && stackSize - 1 < pathIndices.length) {
    pathIndices[stackSize - 1]++;
  }
}
