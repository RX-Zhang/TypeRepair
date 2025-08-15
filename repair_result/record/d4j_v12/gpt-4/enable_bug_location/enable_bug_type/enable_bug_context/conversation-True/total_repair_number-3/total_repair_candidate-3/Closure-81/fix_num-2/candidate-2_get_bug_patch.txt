Node processFunctionNode(FunctionNode functionNode) {
  Name name = functionNode.getFunctionName();
  boolean isUnnamedFunction = false;
  if (name == null) {
    isUnnamedFunction = true;
  }
  Node node = newNode(Token.FUNCTION);
  Node newName;
  if (isUnnamedFunction) {
    newName = newNode(Token.NAME);
    newName.setLineno(functionNode.getLineno());
    newName.setCharno(position2charno(functionNode.getAbsolutePosition()));
  } else {
    newName = transform(name);
  }

  node.addChildToBack(newName);

  Node lp = newNode(Token.LP);
  // The left paren's complicated because it's not represented by an
  // AstNode, so there's nothing that has the actual line number that it
  // appeared on.  We know the paren has to appear on the same line as the
  // function name (or else a semicolon will be inserted.)  If there's no
  // function name, assume the paren was on the same line as the function.
  if (!isUnnamedFunction) {
    lp.setLineno(name.getLineno());
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
