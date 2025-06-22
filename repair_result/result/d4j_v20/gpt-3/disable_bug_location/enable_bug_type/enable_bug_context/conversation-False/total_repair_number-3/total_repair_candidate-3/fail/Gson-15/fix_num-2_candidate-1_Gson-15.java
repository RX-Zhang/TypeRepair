public JsonWriter value(double value) throws IOException {
  if (Double.isNaN(value) || Double.isInfinite(value)) {
    if (!lenient) {
      throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
    }
    writeDeferredName();
    beforeValue();
    out.append(value == Double.NaN ? "NaN"
        : value == Double.POSITIVE_INFINITY ? "Infinity" : "-Infinity");
    return this;
  }
  writeDeferredName();
  beforeValue();
  out.append(Double.toString(value));
  return this;
}
