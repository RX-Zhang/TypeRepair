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

      // Combine first consecutive string literals from the left
      // with proper handling to maintain folding benefits.
      List<Node> newChildren = Lists.newLinkedList();
      StringBuilder leftSb = new StringBuilder();
      int i = 0;
      while (i < arrayFoldedChildren.size()) {
        Node current = arrayFoldedChildren.get(i);
        if (current.getType() == Token.STRING) {
          leftSb.append(NodeUtil.getStringValue(current));
          // Append separator except after last string literal or before non-string
          if (i + 1 < arrayFoldedChildren.size()
              && arrayFoldedChildren.get(i + 1).getType() == Token.STRING) {
            leftSb.append(joinString);
          } else if (i + 1 < arrayFoldedChildren.size()
              && arrayFoldedChildren.get(i + 1).getType() != Token.STRING) {
            leftSb.append(joinString);
          }
          i++;
          // Continue to combine adjacent string nodes
          while (i < arrayFoldedChildren.size() && arrayFoldedChildren.get(i).getType() == Token.STRING) {
            leftSb.append(NodeUtil.getStringValue(arrayFoldedChildren.get(i)));
            if (i + 1 < arrayFoldedChildren.size()) {
              leftSb.append(joinString);
            }
            i++;
          }
          newChildren.add(Node.newString(leftSb.toString()));
          leftSb = new StringBuilder();
        } else {
          newChildren.add(current);
          i++;
        }
      }

      // Now replace the original arrayNode children with newChildren
      foldedSize = 0;
      for (Node node : newChildren) {
        if (node.getType() == Token.STRING) {
          foldedSize += NodeUtil.getStringValue(node).length() + 2;
        } else {
          foldedSize += InlineCostEstimator.getCost(node);
        }
      }
      foldedSize += newChildren.size() - 1; // commas
      foldedSize += "[].join()".length();
      foldedSize += InlineCostEstimator.getCost(right);

      if (foldedSize > originalSize) {
        return;
      }

      arrayNode.detachChildren();
      for (Node node : newChildren) {
        arrayNode.addChildToBack(node);
      }
      break;
  }
  t.getCompiler().reportCodeChange();
}
