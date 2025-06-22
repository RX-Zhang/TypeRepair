private boolean hasExceptionHandler(Node cfgNode) {
  return cfgNode != null && cfgNode.isTry();
}
