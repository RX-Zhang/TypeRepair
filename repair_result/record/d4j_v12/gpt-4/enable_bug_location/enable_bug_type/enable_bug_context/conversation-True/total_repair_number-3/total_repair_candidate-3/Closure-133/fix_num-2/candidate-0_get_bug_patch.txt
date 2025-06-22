private String getRemainingJSDocLine() {
  JsDocToken token = next();
  if (token == null) {
    return "";
  }
  String result = stream.getRemainingJSDocLine();
  restoreLookAhead(token);
  return result;
}
