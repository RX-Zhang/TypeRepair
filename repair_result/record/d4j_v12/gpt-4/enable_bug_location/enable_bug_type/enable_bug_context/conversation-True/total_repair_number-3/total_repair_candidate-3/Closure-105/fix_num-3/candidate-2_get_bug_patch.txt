void tryFoldStringJoin(NodeTraversal t, Node n, Node left, Node right,
                       Node parent) {
  if (!NodeUtil.isGetProp(left) || !NodeUtil.isImmutableValue(right)) {
    return;
  }

  Node arrayNode = left.getFirstChild();
  Node functionName = arrayNode.getNext();

  if ((arrayNode.getType() != Token.ARRAYLIT) ||
      !functionName.getString().equals("join")) {
    return;
  }

  String joinString = NodeUtil.getStringValue(right);
  List<Node> arrayFoldedChildren = Lists.newLinkedList();
  StringBuilder sb = new StringBuilder();
  int foldedSize = 0;
  Node elem = arrayNode.getFirstChild();
  // Merges adjacent String nodes.
  while (elem != null) {
    if (NodeUtil.isImmutableValue(elem)) {
      if (sb.length() > 0) {
        sb.append(joinString);
      }
      sb.append(NodeUtil.getStringValue(elem));
    } else {
      if (sb.length() > 0) {
        // + 2 for the quotes.
        foldedSize += sb.length() + 2;
        arrayFoldedChildren.add(Node.newString(sb.toString()));
        sb = new StringBuilder();
      }
      foldedSize += InlineCostEstimator.getCost(elem);
      arrayFoldedChildren.add(elem);
    }
    elem = elem.getNext();
  }

  if (sb.length() > 0) {
    // + 2 for the quotes.
    foldedSize += sb.length() + 2;
    arrayFoldedChildren.add(Node.newString(sb.toString()));
  }
  // one for each comma.
  foldedSize += arrayFoldedChildren.size() - 1;

  int originalSize = InlineCostEstimator.getCost(n);
  switch (arrayFoldedChildren.size()) {
    case 0:
      Node emptyStringNode = Node.newString("");
      parent.replaceChild(n, emptyStringNode);
      break;

    case 1:
      Node foldedStringNode = arrayFoldedChildren.remove(0);
      if (foldedSize > originalSize) {
        return;
      }
      arrayNode.detachChildren();
      if (foldedStringNode.getType() != Token.STRING) {
        // If the Node is not a string literal, ensure that
        // it is coerced to a string.
        Node replacement = new Node(Token.ADD,
            Node.newString(""), foldedStringNode);
        foldedStringNode = replacement;
      }
      parent.replaceChild(n, foldedStringNode);
      break;

    default:
      // No folding could actually be performed.
      if (arrayFoldedChildren.size() == arrayNode.getChildCount()) {
        return;
      }
      int kJoinOverhead = "[].join()".length();
      foldedSize += kJoinOverhead;
      foldedSize += InlineCostEstimator.getCost(right);
      if (foldedSize > originalSize) {
        return;
      }
      arrayNode.detachChildren();

      // Only fold if this folding reduces the size or keeps it same.
      // Compare original array length to folded array length after merging empty strings appropriately.

      // Merge empty strings in folded array to reduce size where possible
      // This helps optimize cases like ['', '', foo, ''] into [',', foo, ''].
      List<Node> newChildren = Lists.newLinkedList();
      Iterator<Node> it = arrayFoldedChildren.iterator();
      while (it.hasNext()) {
        Node current = it.next();
        if (current.getType() == Token.STRING && current.getString().isEmpty()) {
          // Skip empty string at start or end, or merge with previous string if possible
          if (newChildren.isEmpty()) {
            if (it.hasNext()) {
              // if it's at start, and not the only element, try to merge the join string with next string elements if possible
              continue; // skip
            } else {
              newChildren.add(current); // only one element, keep it
            }
          } else if (!it.hasNext()) {
            // empty string at end
            continue;  // skip
          } else {
            // empty string in middle
            continue; // skip because join string will merge commas anyway
          }
        } else {
          newChildren.add(current);
        }
      }

      // Rebuild arrayNode children
      arrayNode.detachChildren();
      for (Node node : newChildren) {
        arrayNode.addChildToBack(node);
      }
      break;
  }
  t.getCompiler().reportCodeChange();
}
