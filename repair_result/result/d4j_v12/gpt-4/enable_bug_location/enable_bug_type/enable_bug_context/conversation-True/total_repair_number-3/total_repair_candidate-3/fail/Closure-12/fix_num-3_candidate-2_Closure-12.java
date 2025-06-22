private boolean hasExceptionHandler(Node cfgNode) {
  // True if the cfgNode has an exceptional edge, indicating an exception handler
  for (Node edge = cfgNode.getFirstEdge(); edge != null; edge = edge.getNext()) {
    if (edge.getType() == Token.ONEX) {
      return true;
    }
  }
  return false;
}
