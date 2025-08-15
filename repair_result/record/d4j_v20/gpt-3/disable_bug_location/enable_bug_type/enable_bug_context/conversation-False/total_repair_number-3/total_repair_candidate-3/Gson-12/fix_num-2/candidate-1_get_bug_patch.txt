@Override public void skipValue() throws IOException {
  if (peek() == JsonToken.NAME) {
    nextName();
    pathNames[stackSize - 1] = "null";
  } else {
    popStack();
    pathNames[stackSize] = "null";
  }
  if (stackSize > 0) {
    pathIndices[stackSize - 1]++;
  }
}
