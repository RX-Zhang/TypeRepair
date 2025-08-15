public JsonWriter value(double value) throws IOException {
  writeDeferredName();
  if (Double.isNaN(value) || Double.isInfinite(value)) {
    if (!isLenient()) {
      throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
    } else {
      out.append(Double.toString(value));
      return this;
    }
  }
  beforeValue();
  out.append(Double.toString(value));
  return this;
}
