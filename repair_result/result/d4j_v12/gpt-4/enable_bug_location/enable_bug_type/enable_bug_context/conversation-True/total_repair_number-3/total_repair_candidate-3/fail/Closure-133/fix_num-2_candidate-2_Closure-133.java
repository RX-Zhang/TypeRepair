private String getRemainingJSDocLine() {
  JsDocToken token = next();
  if (token == null) {
    return null;
  }
  String result = token.getValue();
  if (result == null) {
    result = "";
  }
  restoreLookAhead(token);
  return result;
}
