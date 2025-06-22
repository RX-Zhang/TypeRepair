@Override public void skipValue() throws IOException {
  if (stackSize == 0) {
    throw new IllegalStateException("Cannot skip value, stack is empty");
  }

  if (peek() == JsonToken.NAME) {
    nextName();
    pathNames[stackSize - 2] = "null";
  } else {
    popStack();
    pathNames[stackSize - 1] = "null";
  }
  pathIndices[stackSize - 1]++;
}
