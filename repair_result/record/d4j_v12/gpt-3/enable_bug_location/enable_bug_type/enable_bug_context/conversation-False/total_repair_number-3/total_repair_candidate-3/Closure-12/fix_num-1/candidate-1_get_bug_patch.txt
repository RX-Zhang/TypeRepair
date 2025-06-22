private boolean hasExceptionHandler(Node cfgNode) {
  return cfgNode.isTry() || cfgNode.isCatch();
}
