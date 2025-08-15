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
    // Fix: 
    // Only skip entering new CFG nodes if the node is NOT a ForIn loop control expression
    // This avoids misclassifying "a in b" expressions inside ForIn loops.
    if (!(ControlFlowGraph.isEnteringNewCfgNode(c) || c.isForIn()))
      if (apply(c)) {
        return true;
      }
  }
  return false;
}
