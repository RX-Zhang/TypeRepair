Node processFunctionNode(FunctionNode functionNode) {
  Name name = functionNode.getFunctionName();
  Boolean isUnnamedFunction = false;
  if (name == null) {
    // Instead of creating a new empty name, treat this as an error
    // unnamed function statements are not allowed
    parseError("unnamed function statement");
  }
  Node node = newNode(Token.FUNCTION);
  Node newName = transform(name);

  if (name == null) {
    // Defensive: if somehow still null, mark with line number
    newName.setLineno(functionNode.getLineno());
    int lpColumn = functionNode.getAbsolutePosition() +
        functionNode.getLp();
    newName.setCharno(position2charno(lpColumn));
  }

  node.addChildToBack(newName);
  Node lp = newNode(Token.LP);
  // The left paren's complicated because it's not represented by an
  // AstNode, so there's nothing that has the actual line number that it
  // appeared on.  We know the paren has to appear on the same line as the
  // function name (or else a semicolon will be inserted.)  If there's no
  // function name, assume the paren was on the same line as the function.
  // TODO(bowdidge): Mark line number of paren correctly.
  if (name != null) {
    lp.setLineno(name.getLineno());
  } else {
    lp.setLineno(functionNode.getLineno());
  }
  int lparenCharno = functionNode.getLp() +
      functionNode.getAbsolutePosition();

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
