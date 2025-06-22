private String getRemainingJSDocLine() {
  StringBuilder result = new StringBuilder();
  skipEOLs();
  JsDocToken token = next();
  while (token != JsDocToken.EOL && token != JsDocToken.EOF) {
    if (token == JsDocToken.STAR) {
      break;
    }
    result.append(token.value);
    token = next();
  }
  restoreLookAhead(token);
  return result.toString();
}
