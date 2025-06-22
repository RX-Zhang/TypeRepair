private VariableLiveness isVariableReadBeforeKill(
    Node n, String variable) {
  if (NodeUtil.isName(n) && variable.equals(n.getString())) {
    if (NodeUtil.isLhs(n, n.getParent())) {
      // The LHS of an assignment (variable = ...) kills the variable after the RHS.
      return VariableLiveness.KILL;
    } else {
      return VariableLiveness.READ;
    }
  }

  VariableLiveness result = VariableLiveness.MAYBE_LIVE;
  // Expressions are evaluated left-right, depth first.
  for (Node child = n.getFirstChild();
      child != null; child = child.getNext()) {
    if (!ControlFlowGraph.isEnteringNewCfgNode(child)) { // Not a FUNCTION
      VariableLiveness state = isVariableReadBeforeKill(child, variable);
      if (state == VariableLiveness.READ) {
        return VariableLiveness.READ;
      }
      if (state == VariableLiveness.KILL) {
        // Keep track of kill, but keep searching for possible read
        result = VariableLiveness.KILL;
      }
    }
  }
  return result;
}
