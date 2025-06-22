public JsonWriter value(double value) throws IOException {
  if (Double.isNaN(value) || Double.isInfinite(value)) {
    if (lenient) {
      writeDeferredName();
      beforeValue();
      out.append(Double.toString(value));
      return this;
    } else {
      throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
    }
  }
  writeDeferredName();
  beforeValue();
  out.append(Double.toString(value));
  return this;
}
