public JsonWriter value(double value) throws IOException {
  writeDeferredName();
  // Check if lenient mode is not enabled for non-finite values
  if (!lenient && (Double.isNaN(value) || Double.isInfinite(value))) {
    throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
  }
  beforeValue();
  out.append(Double.toString(value));
  return this;
}
