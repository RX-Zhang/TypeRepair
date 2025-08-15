public JsonWriter value(double value) throws IOException {
  writeDeferredName();
  if ((Double.isNaN(value) || Double.isInfinite(value)) && !isLenient()) {
    throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
  }
  beforeValue();
  if (Double.isNaN(value) || Double.isInfinite(value)) {
    // Output the string representation of non-finite values for lenient mode
    out.append(value == Double.NaN ? "NaN" : (value == Double.POSITIVE_INFINITY ? "Infinity" : "-Infinity"));
  } else {
    out.append(Double.toString(value));
  }
  return this;
}
