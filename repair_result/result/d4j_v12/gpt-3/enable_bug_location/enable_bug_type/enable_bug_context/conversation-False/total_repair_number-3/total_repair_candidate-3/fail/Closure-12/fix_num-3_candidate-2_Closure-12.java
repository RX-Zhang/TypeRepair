private boolean hasExceptionHandler(Node cfgNode) {
  for (DiGraphEdge<Node, ControlFlowGraph.Branch> edge : cfgNode.getEdgesOut()) {
    if (edge.getValue() == ControlFlowGraph.Branch.ON_EX) {
      return true;
    }
  }
  return false;
}
