@Override public void skipValue() throws IOException {
  if (stackSize == 0) {
    throw new IllegalStateException("No value to skip, stack is empty");
  }
  
  if (peek() == JsonToken.NAME) {
    nextName();
    pathNames[stackSize - 2] = "null";
  } else {
    popStack();
    pathNames[stackSize - 1] = "null";
  }
  
  if (stackSize > 0) {
    pathIndices[stackSize - 1]++;
  } else {
    throw new IllegalStateException("Cannot increment path index, stack is empty");
  }
}
