public Number read(JsonReader in) throws IOException {
  JsonToken jsonToken = in.peek();
  switch (jsonToken) {
  case NULL:
    in.nextNull();
    return null;
  case NUMBER:
    return new LazilyParsedNumber(in.nextString());
  case STRING:
    // Handle the case where the number is represented as a string
    String numberStr = in.nextString();
    try {
      return new LazilyParsedNumber(numberStr);
    } catch (NumberFormatException e) {
      throw new JsonSyntaxException("Expecting number, got: STRING", e);
    }
  default:
    throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
  }
}
