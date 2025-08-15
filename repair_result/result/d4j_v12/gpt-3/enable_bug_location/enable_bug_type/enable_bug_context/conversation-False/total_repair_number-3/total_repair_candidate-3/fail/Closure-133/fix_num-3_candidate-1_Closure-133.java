private String getRemainingJSDocLine() {
  try {
    String result = stream.getRemainingJSDocLine();
    return result;
  } catch (IllegalStateException e) {
    // Handle or log the exception appropriately, returning empty to avoid crash
    return "";
  }
}
