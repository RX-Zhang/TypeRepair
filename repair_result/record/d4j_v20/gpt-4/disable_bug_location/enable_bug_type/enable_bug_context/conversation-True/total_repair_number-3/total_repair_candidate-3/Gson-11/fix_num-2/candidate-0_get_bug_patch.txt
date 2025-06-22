public Number read(JsonReader in) throws IOException {
  JsonToken jsonToken = in.peek();
  switch (jsonToken) {
  case NULL:
    in.nextNull();
    return null;
  case NUMBER:
    return new LazilyParsedNumber(in.nextString());
  case STRING:
    // Try to parse the string as a number to support Number deserialization from JSON strings
    String stringValue = in.nextString();
    try {
      return new LazilyParsedNumber(stringValue);
    } catch (NumberFormatException e) {
      throw new JsonSyntaxException("Expecting number, got: STRING", e);
    }
  default:
    throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
  }
}
