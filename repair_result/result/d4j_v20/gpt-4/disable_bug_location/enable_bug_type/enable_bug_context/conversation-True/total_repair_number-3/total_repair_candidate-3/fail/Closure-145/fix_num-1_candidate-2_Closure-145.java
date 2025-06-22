private boolean isOneExactlyFunctionOrDo(Node n) {
    // For labels with block children, we need to ensure that a
    // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
    // are skipped. 
    // Either an empty statement or a block with more than one child,
    // way it isn't a FUNCTION or DO.
    return (n.getType() == Token.FUNCTION || n.getType() == Token.DO);
}

private void addNonEmptyExpression(
    Node n, Context context, boolean allowNonBlockChild) {
  Node nodeToProcess = n;

  if (!allowNonBlockChild && n.getType() != Token.BLOCK) {
    throw new Error("Missing BLOCK child.");
  }

  // Strip unneeded blocks, that is blocks with <2 children unless
  // the CodePrinter specifically wants to keep them.
  if (n.getType() == Token.BLOCK) {
    int count = getNonEmptyChildCount(n, 2);
    if (count == 0) {
      if (cc.shouldPreserveExtraBlocks()) {
        cc.beginBlock();
        cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
      } else {
        cc.endStatement(true);
      }
      return;
    }

    if (count == 1) {
      // Hack around a couple of browser bugs:
      //   Safari needs a block around function declarations.
      //   IE6/7 needs a block around DOs.
      Node firstAndOnlyChild = getFirstNonEmptyChild(n);
      boolean alwaysWrapInBlock = cc.shouldPreserveExtraBlocks();
      if (alwaysWrapInBlock || isOneExactlyFunctionOrDo(firstAndOnlyChild)) {
        cc.beginBlock();
        add(firstAndOnlyChild, Context.STATEMENT);
        cc.maybeLineBreak();
        cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
        return;
      } else {
        // Continue with the only child.
        nodeToProcess = firstAndOnlyChild;
      }
    }
  }

  if (nodeToProcess.getType() == Token.EMPTY) {
    cc.endStatement(true);
  } else {
    add(nodeToProcess, context);

    // VAR doesn't include ';' since it gets used in expressions - so any
    // VAR in a statement context needs a call to endStatement() here.
    if (nodeToProcess.getType() == Token.VAR) {
      cc.endStatement();
    }
  }
}
