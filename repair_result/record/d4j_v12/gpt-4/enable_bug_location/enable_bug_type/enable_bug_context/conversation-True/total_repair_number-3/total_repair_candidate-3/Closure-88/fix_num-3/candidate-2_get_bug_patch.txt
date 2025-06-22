private VariableLiveness isVariableReadBeforeKill(
    Node n, String variable) {
  if (NodeUtil.isName(n) && variable.equals(n.getString())) {
    if (NodeUtil.isLhs(n, n.getParent())) {
      // The expression to which the assignment is made is evaluated before
      // the RHS is evaluated (normal left to right evaluation) but the KILL
      // occurs after the RHS is evaluated.
      // We must still check if the variable is read inside the RHS expression before it is overwritten.
      // So instead of immediately returning KILL, we continue to check the RHS child node.
      VariableLiveness rhsState = VariableLiveness.MAYBE_LIVE;
      Node rhs = n.getParent().getLastChild();
      if (rhs != n) { // RHS exists and is different from the LHS node itself
        rhsState = isVariableReadBeforeKill(rhs, variable);
      }
      if (rhsState == VariableLiveness.READ) {
        return VariableLiveness.READ;
      } else {
        return VariableLiveness.KILL;
      }
    } else {
      return VariableLiveness.READ;
    }
  }

  // Expressions are evaluated left-right, depth first.
  for (Node child = n.getFirstChild();
      child != null; child = child.getNext()) {
    if (!ControlFlowGraph.isEnteringNewCfgNode(child)) { // Not a FUNCTION
      VariableLiveness state = isVariableReadBeforeKill(child, variable);
      if (state != VariableLiveness.MAYBE_LIVE) {
        return state;
      }
    }
  }
  return VariableLiveness.MAYBE_LIVE;
}
