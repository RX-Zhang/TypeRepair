public Number read(JsonReader in) throws IOException {
  JsonToken jsonToken = in.peek();
  switch (jsonToken) {
  case NULL:
    in.nextNull();
    return null;
  case NUMBER:
    return new LazilyParsedNumber(in.nextString());
  case STRING:
    // Allow numbers represented as strings, e.g. "18"
    String stringValue = in.nextString();
    try {
      return new LazilyParsedNumber(stringValue);
    } catch (NumberFormatException e) {
      throw new JsonSyntaxException("Expecting number, got: " + stringValue, e);
    }
  default:
    throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
  }
}
