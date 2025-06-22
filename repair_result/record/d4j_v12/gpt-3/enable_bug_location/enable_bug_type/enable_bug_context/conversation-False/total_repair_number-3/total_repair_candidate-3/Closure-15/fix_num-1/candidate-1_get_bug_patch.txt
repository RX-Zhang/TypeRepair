public boolean apply(Node n) {
  // When the node is null it means we reached the implicit return
  // where the function returns (possibly without a return statement)
  if (n == null) {
    return false;
  }

  // TODO(user): We only care about calls to functions that
  // pass one of the dependent variables to a non-side-effect free
  // function.
  if (n.isCall() && NodeUtil.functionCallHasSideEffects(n)) {
    return true;
  }

  if (n.isNew() && NodeUtil.constructorCallHasSideEffects(n)) {
    return true;
  }

  for (Node c = n.getFirstChild(); c != null; c = c.getNext()) {
    // We should recurse only if we are NOT entering a new CFG node.
    // So we continue only if isEnteringNewCfgNode(c) == false.
    // The original code had: if (!ControlFlowGraph.isEnteringNewCfgNode(c) && apply(c))
    // This is logically correct, but it is important to make sure we don't short-circuit too early.
    if (!ControlFlowGraph.isEnteringNewCfgNode(c) && apply(c)) {
      return true;
    }
  }

  return false;
}
