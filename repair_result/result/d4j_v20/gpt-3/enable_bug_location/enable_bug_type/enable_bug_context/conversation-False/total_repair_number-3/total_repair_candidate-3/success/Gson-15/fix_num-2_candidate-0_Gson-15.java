public JsonWriter value(double value) throws IOException {
  writeDeferredName();
  // Check for NaN and infinite values only if not lenient
  if (!lenient && (Double.isNaN(value) || Double.isInfinite(value))) {
    throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
  }
  beforeValue();
  out.append(Double.toString(value));
  return this;
}
