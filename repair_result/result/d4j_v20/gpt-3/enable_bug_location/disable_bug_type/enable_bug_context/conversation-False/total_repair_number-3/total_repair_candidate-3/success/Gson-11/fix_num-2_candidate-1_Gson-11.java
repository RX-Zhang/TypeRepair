public Number read(JsonReader in) throws IOException {
  JsonToken jsonToken = in.peek();
  switch (jsonToken) {
  case NULL:
    in.nextNull();
    return null;
  case NUMBER:
    return new LazilyParsedNumber(in.nextString());
  case STRING:
    String s = in.nextString();
    try {
      return new LazilyParsedNumber(s);
    } catch (NumberFormatException e) {
      throw new JsonSyntaxException("Expecting number, got: " + s, e);
    }
  default:
    throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
  }
}
