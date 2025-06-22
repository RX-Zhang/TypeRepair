private String getRemainingJSDocLine() {
  JsDocToken token = next();
  if (token == null) {
    return "";
  }

  if (token.getType() == JsDocToken.Type.JSDOC_EOL) {
    // skip EOL tokens
    skipEOLs();
    token = next();
  }

  if (token == null) {
    return "";
  }

  String result = stream.getRemainingJSDocLine();

  // Restore the token so it can be reprocessed if needed
  restoreLookAhead(token);

  // Sanitize or validate the result before returning
  if (result == null) {
    return "";
  }

  // Optionally trim trailing whitespace or sanitize further 
  return result.trim();
}
