private String getRemainingJSDocLine() {
  JsDocToken token = next();
  StringBuilder sb = new StringBuilder();
  while (token != JsDocToken.EOL && token != JsDocToken.EOF) {
    sb.append(token.getValue());
    token = next();
  }
  restoreLookAhead(token);
  return sb.toString();
}
