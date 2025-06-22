@Override public void skipValue() throws IOException {
  if (peek() == JsonToken.NAME) {
    nextName();
    if (stackSize - 2 >= 0) {
      pathNames[stackSize - 2] = "null";
    }
  } else {
    popStack();
    if (stackSize - 1 >= 0) {
      pathNames[stackSize - 1] = "null";
    }
  }
  if (stackSize - 1 >= 0) {
    pathIndices[stackSize - 1]++;
  }
}
