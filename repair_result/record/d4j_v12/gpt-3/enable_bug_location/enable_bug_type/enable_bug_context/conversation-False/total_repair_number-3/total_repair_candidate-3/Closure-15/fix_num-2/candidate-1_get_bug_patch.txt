public boolean apply(Node n) {
  // When the node is null it means, we reached the implicit return
  // where the function returns (possibly without an return statement)
  if (n == null) {
    return false;
  }

  // TODO(user): We only care about calls to functions that
  // pass one of the dependent variables to a non-side-effect free
  // function.
  if (n.isCall() && !NodeUtil.functionCallHasSideEffects(n)) {
    // No side effects, so continue checking children
  } else if (n.isCall() && NodeUtil.functionCallHasSideEffects(n)) {
    return true;
  }

  if (n.isNew() && NodeUtil.constructorCallHasSideEffects(n)) {
    return true;
  }

  for (Node c = n.getFirstChild(); c != null; c = c.getNext()) {
    // The original logic skipped children that start new CFG nodes.
    // This is incorrect if those children may have side effects.
    // Change to always check children.
    if (apply(c)) {
      return true;
    }
  }
  return false;
}
