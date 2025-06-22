private String getRemainingJSDocLine() {
  try {
    String result = stream.getRemainingJSDocLine();
    return result;
  } catch (IllegalStateException e) {
    // Return empty string or handle gracefully to prevent crashing
    return "";
  }
}
