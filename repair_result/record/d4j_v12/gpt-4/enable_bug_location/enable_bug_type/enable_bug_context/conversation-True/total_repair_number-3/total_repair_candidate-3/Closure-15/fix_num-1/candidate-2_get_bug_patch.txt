public boolean apply(Node n) {
  // When the node is null it means, we reached the implicit return
  // where the function returns (possibly without an return statement)
  if (n == null) {
    return false;
  }

  // TODO(user): We only care about calls to functions that
  // passes one of the dependent variable to a non-side-effect free
  // function.
  if (n.isCall() && NodeUtil.functionCallHasSideEffects(n)) {
    return true;
  }

  if (n.isNew() && NodeUtil.constructorCallHasSideEffects(n)) {
    return true;
  }


  for (Node c = n.getFirstChild(); c != null; c = c.getNext()) {
    // Fix: If we are entering a new CFG node, skip recursion into that node,
    // but ONLY if the current node is NOT itself a new CFG node.
    // This prevents reordering side-effecting expressions like "a in b"
    if (!ControlFlowGraph.isEnteringNewCfgNode(c) || ControlFlowGraph.isEnteringNewCfgNode(n)) {
      if (apply(c)) {
        return true;
      }
    }
  }
  return false;
}
