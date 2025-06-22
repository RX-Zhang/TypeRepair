public boolean apply(Node n) {
  // When the node is null it means, we reached the implicit return
  // where the function returns (possibly without a return statement)
  if (n == null) {
    return false;
  }

  // TODO(user): We only care about calls to functions that
  // pass one of the dependent variables to a non-side-effect free
  // function.
  if (n.isCall() && NodeUtil.callHasSideEffects(n, compiler)) {
    return true;
  }

  if (n.isNew() && NodeUtil.constructorCallHasSideEffects(n, compiler)) {
    return true;
  }

  for (Node c = n.getFirstChild(); c != null; c = c.getNext()) {
    if (!ControlFlowGraph.isEnteringNewCfgNode(c) && apply(c)) {
      return true;
    }
  }
  return false;
}
