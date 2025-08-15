public JsonWriter value(double value) throws IOException {
  writeDeferredName();
  // Handle non-finite values based on the leniency of the writer
  if (!lenient && (Double.isNaN(value) || Double.isInfinite(value))) {
    throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
  }
  beforeValue();
  out.append(Double.toString(value));
  return this;
}
