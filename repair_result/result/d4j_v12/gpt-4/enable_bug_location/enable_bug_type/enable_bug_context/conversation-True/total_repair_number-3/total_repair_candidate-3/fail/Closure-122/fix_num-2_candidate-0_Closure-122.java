private void handleBlockComment(Comment comment) {
  String value = comment.getValue();
  // Only warn if the comment contains JSDoc-style annotations but does NOT start with '/**'
  if ((value.indexOf("@") != -1) && !value.startsWith("/**")) {
    errorReporter.warning(
        SUSPICIOUS_COMMENT_WARNING,
        sourceName,
        comment.getLineno(), "", 0);
  }
}
