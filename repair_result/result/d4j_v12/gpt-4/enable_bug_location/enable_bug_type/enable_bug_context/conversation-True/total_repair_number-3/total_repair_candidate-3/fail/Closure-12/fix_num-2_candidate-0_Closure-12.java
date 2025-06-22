private boolean hasExceptionHandler(Node cfgNode) {
  for (DiGraphEdge<Node, ControlFlowGraph.Branch> edge : cfgNode.getOutEdges()) {
    if (edge.getValue().isException()) {
      return true;
    }
  }
  return false;
}
