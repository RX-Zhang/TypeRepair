public JsonWriter value(double value) throws IOException {
  writeDeferredName();
  if (Double.isNaN(value) || Double.isInfinite(value)) {
    if (!lenient) {
      throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
    } else {
      out.append(value == Double.NaN ? "NaN" : (value == Double.POSITIVE_INFINITY ? "Infinity" : "-Infinity"));
      return this;
    }
  }
  beforeValue();
  out.append(Double.toString(value));
  return this;
}
