private void handleBlockComment(Comment comment) {
  String value = comment.getValue();
  // Check if the comment starts with '/**' indicating a JSDoc comment, skip those.
  if (value.startsWith("/**")) {
    return;
  }
  // Check if the comment contains annotations starting with '@'
  if (value.indexOf("@") != -1) {
    errorReporter.warning(
        SUSPICIOUS_COMMENT_WARNING,
        sourceName,
        comment.getLineno(), "", 0);
  }
}
