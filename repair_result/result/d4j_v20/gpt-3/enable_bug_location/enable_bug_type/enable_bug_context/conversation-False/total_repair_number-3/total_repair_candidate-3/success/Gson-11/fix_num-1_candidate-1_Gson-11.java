public Number read(JsonReader in) throws IOException {
  JsonToken jsonToken = in.peek();
  switch (jsonToken) {
  case NULL:
    in.nextNull();
    return null;
  case NUMBER:
    return new LazilyParsedNumber(in.nextString());
  case STRING: // Added handling for STRING case
    String stringValue = in.nextString();
    try {
      return Integer.parseInt(stringValue); // Attempt to parse the string as an integer
    } catch (NumberFormatException e) {
      throw new JsonSyntaxException("Expecting number, got: STRING with value: " + stringValue, e);
    }
  default:
    throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
  }
}
