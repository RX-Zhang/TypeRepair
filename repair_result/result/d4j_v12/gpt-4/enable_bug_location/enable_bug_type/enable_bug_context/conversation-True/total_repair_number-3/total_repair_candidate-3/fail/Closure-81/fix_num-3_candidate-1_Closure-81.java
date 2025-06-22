Node processFunctionNode(FunctionNode functionNode) {
  Name name = functionNode.getFunctionName();
  Node node = newNode(Token.FUNCTION);
  Node newName;

  if (name == null || name.getIdentifier().isEmpty()) {
    // unnamed function expression or statement
    if (functionNode.getParent() != null && functionNode.getParent() instanceof ExpressionStatement) {
      // unnamed function statement is a syntax error
      errorReporter.reportError("unnamed function statement", functionNode.getLineno());
    }
    // create an empty name node with the line number of the declaration
    name = new Name();
    name.setIdentifier("");
    newName = newNode(Token.NAME);
    newName.setLineno(functionNode.getLineno());
    newName.setCharno(position2charno(functionNode.getAbsolutePosition()));
  } else {
    newName = transform(name);
  }

  node.addChildToBack(newName);

  Node lp = newNode(Token.LP);
  Name fnName = functionNode.getFunctionName();
  if (fnName != null) {
    lp.setLineno(fnName.getLineno());
  } else {
    lp.setLineno(functionNode.getLineno());
  }
  int lparenCharno = functionNode.getLp() + functionNode.getAbsolutePosition();
  lp.setCharno(position2charno(lparenCharno));

  for (AstNode param : functionNode.getParams()) {
    lp.addChildToBack(transform(param));
  }
  node.addChildToBack(lp);

  Node bodyNode = transform(functionNode.getBody());
  parseDirectives(bodyNode);
  node.addChildToBack(bodyNode);
  return node;
}
