private boolean hasExceptionHandler(Node cfgNode) {
  return cfgNode != null && cfgNode.getFirstChild() != null && cfgNode.getFirstChild().isTry();
}
