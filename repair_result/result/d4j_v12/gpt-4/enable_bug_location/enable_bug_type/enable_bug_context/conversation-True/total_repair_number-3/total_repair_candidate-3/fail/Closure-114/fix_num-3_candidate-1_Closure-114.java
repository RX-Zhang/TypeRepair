private void recordAssignment(NodeTraversal t, Node n, Node recordNode) {
  Node nameNode = n.getFirstChild();
  Node parent = n.getParent();
  NameInformation ns = createNameInformation(t, nameNode);
  if (ns != null) {
    if (parent.isFor() && !NodeUtil.isForIn(parent)) {
      // Patch for assignments that appear in the init,
      // condition or iteration part of a FOR loop.  Without
      // this change, all 3 of those parts try to claim the for
      // loop as their dependency scope.  The last assignment in
      // those three fields wins, which can result in incorrect
      // reference edges between referenced and assigned variables.
      //
      // TODO(user) revisit the dependency scope calculation
      // logic.
      if (parent.getFirstChild().getNext() != n) {
        recordDepScope(recordNode, ns);
      } else {
        recordDepScope(nameNode, ns);
      }
    } else {
      // The rhs of the assignment may be consumed by the parent.
      // If the assignment is used as a call (e.g., (fun = function(){})()),
      // we do not want to associate it with the lhs since the function is
      // invoked immediately. This can cause incorrect removal of vars.
      if (!valueConsumedByParent(n, parent)) {
        recordDepScope(recordNode, ns);
      }
    }
  }
}
