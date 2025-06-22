public JsonWriter value(double value) throws IOException {
  writeDeferredName();
  if (Double.isNaN(value) || Double.isInfinite(value)) {
    if (lenient) {
      // If lenient mode is enabled, we can handle non-finite values
      out.append(Double.toString(value));
      return this;
    } else {
      throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
    }
  }
  beforeValue();
  out.append(Double.toString(value));
  return this;
}
