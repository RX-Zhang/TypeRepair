public Number read(JsonReader in) throws IOException {
  JsonToken jsonToken = in.peek();
  switch (jsonToken) {
  case NULL:
    in.nextNull();
    return null;
  case NUMBER:
    return new LazilyParsedNumber(in.nextString());
  case STRING:
    // Attempt to parse the string as a number
    String numberStr = in.nextString();
    try {
      return new LazilyParsedNumber(numberStr);
    } catch (NumberFormatException e) {
      throw new JsonSyntaxException("Expecting number, got: " + numberStr, e);
    }
  default:
    throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
  }
}
