public JsonWriter value(double value) throws IOException {
  if (Double.isNaN(value) || Double.isInfinite(value)) {
    if (!lenient) {
      throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
    }
    writeDeferredName();
    beforeValue();
    out.append(value > 0 ? "Infinity" : (value < 0 ? "-Infinity" : "NaN"));
    return this;
  }
  writeDeferredName();
  beforeValue();
  out.append(Double.toString(value));
  return this;
}
