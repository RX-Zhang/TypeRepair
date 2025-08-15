private void replaceAssignmentExpression(Var v, Reference ref,
                                         Map<String, String> varmap) {
  // Compute all of the assignments necessary
  List<Node> nodes = Lists.newArrayList();
  Node val = ref.getAssignedValue();
  blacklistVarReferencesInTree(val, v.scope);
  Preconditions.checkState(val.getType() == Token.OBJECTLIT);
  Set<String> all = Sets.newLinkedHashSet(varmap.keySet());
  for (Node key = val.getFirstChild(); key != null;
       key = key.getNext()) {
    String var = key.getString();
    Node value = key.removeFirstChild();
    // TODO(user): Copy type information.
    nodes.add(
      new Node(Token.ASSIGN,
               Node.newString(Token.NAME, varmap.get(var)), value));
    all.remove(var);
  }

  // TODO(user): Better source information.
  for (String var : all) {
    nodes.add(
      new Node(Token.ASSIGN,
               Node.newString(Token.NAME, varmap.get(var)),
               NodeUtil.newUndefinedNode(null)));
  }

  Node replacement;
  // All assignments evaluate to true, so make sure that the
  // expr statement evaluates to true in case it matters.
  nodes.add(new Node(Token.TRUE));

  // Join these using COMMA.  A COMMA node must have 2 children, so we
  // create a tree. In the tree the first child be the COMMA to match
  // the parser, otherwise tree equality tests fail.
  nodes = Lists.reverse(nodes);
  replacement = new Node(Token.COMMA);
  Node cur = replacement;

  int size = nodes.size();
  if (size == 1) {
    // Only TRUE node, so just use that
    replacement = nodes.get(0);
  } else if (size == 2) {
    // Just a single COMMA with two children
    replacement.addChildToFront(nodes.get(0));
    replacement.addChildToFront(nodes.get(1));
  } else {
    // More than two nodes, build a left-heavy COMMA tree
    for (int i = 0; i < size - 2; i++) {
      cur.addChildToFront(nodes.get(i));
      Node t = new Node(Token.COMMA);
      cur.addChildToFront(t);
      cur = t;
    }
    cur.addChildToFront(nodes.get(size - 2));
    cur.addChildToFront(nodes.get(size - 1));
  }

  Node replace = ref.getParent();
  replacement.copyInformationFromForTree(replace);

  if (replace.getType() == Token.VAR) {
    replace.getParent().replaceChild(
        replace, NodeUtil.newExpr(replacement));
  } else {
    replace.getParent().replaceChild(replace, replacement);
  }
}
