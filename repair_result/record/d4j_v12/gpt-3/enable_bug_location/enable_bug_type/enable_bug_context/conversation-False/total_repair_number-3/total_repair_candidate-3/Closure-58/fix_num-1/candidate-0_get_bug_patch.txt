private void computeGenKill(Node n, BitSet gen, BitSet kill,
    boolean conditional) {

  switch (n.getType()) {
    case Token.SCRIPT:
    case Token.BLOCK:
    case Token.FUNCTION:
      return;

    case Token.WHILE:
    case Token.DO:
    case Token.IF:
      computeGenKill(NodeUtil.getConditionExpression(n), gen, kill,
          conditional);
      return;

    case Token.FOR:
      if (!NodeUtil.isForIn(n)) {
        computeGenKill(NodeUtil.getConditionExpression(n), gen, kill,
            conditional);
      } else {
        // for(x in y) {...}
        Node lhs = n.getFirstChild();
        Node rhs = lhs.getNext();
        if (NodeUtil.isVar(lhs)) {
          // for(var x in y) {...}
          lhs = lhs.getLastChild();
        }
        try {
          addToSetIfLocal(lhs, kill);
          addToSetIfLocal(lhs, gen);
        } catch (IllegalStateException e) {
          // Defensive: ignore if variable not declared or escaped
        }
        computeGenKill(rhs, gen, kill, conditional);
      }
      return;

    case Token.VAR:
      for (Node c = n.getFirstChild(); c != null; c = c.getNext()) {
        if (c.hasChildren()) {
          computeGenKill(c.getFirstChild(), gen, kill, conditional);
          if (!conditional) {
            try {
              addToSetIfLocal(c, kill);
            } catch (IllegalStateException e) {
              // Defensive: ignore if variable not declared or escaped
            }
          }
        }
      }
      return;

    case Token.AND:
    case Token.OR:
      computeGenKill(n.getFirstChild(), gen, kill, conditional);
      // May short circuit.
      computeGenKill(n.getLastChild(), gen, kill, true);
      return;

    case Token.HOOK:
      computeGenKill(n.getFirstChild(), gen, kill, conditional);
      // Assume both sides are conditional.
      computeGenKill(n.getFirstChild().getNext(), gen, kill, true);
      computeGenKill(n.getLastChild(), gen, kill, true);
      return;

    case Token.NAME:
      if (isArgumentsName(n)) {
        markAllParametersEscaped();
      } else {
        try {
          addToSetIfLocal(n, gen);
        } catch (IllegalStateException e) {
          // Defensive: ignore if variable not declared or escaped
        }
      }
      return;

    default:
      if (NodeUtil.isAssignmentOp(n) && NodeUtil.isName(n.getFirstChild())) {
        Node lhs = n.getFirstChild();
        if (!conditional) {
          try {
            addToSetIfLocal(lhs, kill);
          } catch (IllegalStateException e) {
            // Defensive: ignore if variable not declared or escaped
          }
        }
        if (!NodeUtil.isAssign(n)) {
          // assignments such as a += 1 reads a.
          try {
            addToSetIfLocal(lhs, gen);
          } catch (IllegalStateException e) {
            // Defensive: ignore if variable not declared or escaped
          }
        }
        computeGenKill(lhs.getNext(), gen, kill, conditional);
      } else {
        for (Node c = n.getFirstChild(); c != null; c = c.getNext()) {
          computeGenKill(c, gen, kill, conditional);
        }
      }
      return;
  }
}
