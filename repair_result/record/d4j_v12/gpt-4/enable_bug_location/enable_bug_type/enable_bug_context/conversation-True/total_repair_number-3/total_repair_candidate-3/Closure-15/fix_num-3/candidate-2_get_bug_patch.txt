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
    // Fix: Skip entering a new CFG node only if c is a new CFG node,
    // but we should continue traversal on those children that enter new CFG nodes.
    // So, changing condition to only skip those c which cause new CFG node.
    if (ControlFlowGraph.isEnteringNewCfgNode(c) || apply(c)) {
      return true;
    }
  }
  return false;
}
