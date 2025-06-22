private String getRemainingJSDocLine() {
  JsDocToken token = next();
  StringBuilder sb = new StringBuilder();
  while (token != null && token != JsDocToken.EOL && token != JsDocToken.EOF) {
    sb.append(token.value);
    token = next();
  }
  if (token == JsDocToken.EOL) {
    restoreLookAhead(token);
  }
  return sb.toString();
}
