private String getRemainingJSDocLine() {
  try {
    String result = stream.getRemainingJSDocLine();
    if (result == null) {
      return "";
    }
    return result;
  } catch (Exception e) {
    // In case of unexpected errors, return empty string to avoid propagation
    return "";
  }
}
