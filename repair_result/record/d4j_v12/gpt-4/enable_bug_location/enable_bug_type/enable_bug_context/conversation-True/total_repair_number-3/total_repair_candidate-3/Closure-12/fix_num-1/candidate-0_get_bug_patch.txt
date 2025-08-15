private boolean hasExceptionHandler(Node cfgNode) {
  for (Node edge : cfgNode.getEdges()) {
    if (edge.isOnExceptionEdge()) {
      return true;
    }
  }
  return false;
}
