public Number read(JsonReader in) throws IOException {
  JsonToken jsonToken = in.peek();
  switch (jsonToken) {
  case NULL:
    in.nextNull();
    return null;
  case NUMBER:
    return new LazilyParsedNumber(in.nextString());
  case STRING: // Added case to handle STRING input
    try {
      return new LazilyParsedNumber(in.nextString());
    } catch (NumberFormatException e) {
      throw new JsonSyntaxException("Expecting number, got: STRING");
    }
  default:
    throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
  }
}
