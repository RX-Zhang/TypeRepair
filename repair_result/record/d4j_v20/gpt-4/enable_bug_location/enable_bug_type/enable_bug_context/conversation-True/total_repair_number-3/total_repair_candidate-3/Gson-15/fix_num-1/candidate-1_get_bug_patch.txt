public JsonWriter value(double value) throws IOException {
  writeDeferredName();
  if ((Double.isNaN(value) || Double.isInfinite(value)) && !isLenient()) {
    throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
  }
  beforeValue();
  if (Double.isNaN(value)) {
    out.append("NaN");
  } else if (Double.isInfinite(value)) {
    out.append(value > 0 ? "Infinity" : "-Infinity");
  } else {
    out.append(Double.toString(value));
  }
  return this;
}
