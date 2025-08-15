private String getRemainingJSDocLine() {
  // Check if there is any unread token from previous parsing attempt
  if (unreadToken != NO_UNREAD_TOKEN) {
    JsDocToken tokenToReturn = unreadToken;
    unreadToken = NO_UNREAD_TOKEN;
    return tokenToReturn.value;
  }

  String result = stream.getRemainingJSDocLine();

  // Basic input validation: ensure the line is not null or empty
  if (result == null) {
    errorReporter.warning("Unexpected end of JSDoc line", null, -1, -1);
    return "";
  }

  // Sanitize input by trimming trailing whitespace; 
  // Can add more sanitization if needed
  result = trimEnd(result);
  return result;
}
