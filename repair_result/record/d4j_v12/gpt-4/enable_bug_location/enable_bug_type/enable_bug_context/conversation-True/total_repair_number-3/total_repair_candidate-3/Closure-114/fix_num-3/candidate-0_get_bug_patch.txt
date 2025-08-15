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
      // Check if the assignment value is consumed by the parent node.
      // If the value is not consumed (e.g., an assignment used as a caller),
      // then we should not associate the dependency scope here.
      if (valueConsumedByParent(n, parent)) {
        recordDepScope(recordNode, ns);
      }
    }
  }
}
