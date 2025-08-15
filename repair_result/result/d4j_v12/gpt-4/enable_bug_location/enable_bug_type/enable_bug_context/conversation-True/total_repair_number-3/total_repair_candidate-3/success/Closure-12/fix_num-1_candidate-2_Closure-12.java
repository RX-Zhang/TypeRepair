private boolean hasExceptionHandler(Node cfgNode) {
  // Check if the cfgNode or any of its relevant ancestors has an exception handler
  Node current = cfgNode;
  while (current != null) {
    if (current.isTry()) {
      return true;
    }
    current = current.getParent();
  }
  return false;
}
