private Node tryFoldArrayJoin(Node n) {
  Node callTarget = n.getFirstChild();

  if (callTarget == null || !NodeUtil.isGetProp(callTarget)) {
    return n;
  }

  Node right = callTarget.getNext();
  if (right != null) {
    if (!NodeUtil.isImmutableValue(right)) {
      return n;
    }
  }

  Node arrayNode = callTarget.getFirstChild();
  Node functionName = arrayNode.getNext();

  if ((arrayNode.getType() != Token.ARRAYLIT) ||
      !functionName.getString().equals("join")) {
    return n;
  }

  // "," is the default, it doesn't need to be explicit
  String joinString = (right == null) ? "," : NodeUtil.getStringValue(right);
  List<Node> arrayFoldedChildren = Lists.newLinkedList();
  StringBuilder sb = null;
  int foldedSize = 0;
  Node prev = null;
  Node elem = arrayNode.getFirstChild();
  // Merges adjacent String nodes.
  while (elem != null) {
    if (NodeUtil.isImmutableValue(elem) || elem.getType() == Token.EMPTY) {
      if (sb == null) {
        sb = new StringBuilder();
      } else {
        sb.append(joinString);
      }
      sb.append(NodeUtil.getArrayElementStringValue(elem));
    } else {
      if (sb != null) {
        Preconditions.checkNotNull(prev);
        // + 2 for the quotes.
        foldedSize += sb.length() + 2;
        arrayFoldedChildren.add(
            Node.newString(sb.toString()).copyInformationFrom(prev));
        sb = null;
      }
      foldedSize += InlineCostEstimator.getCost(elem);
      arrayFoldedChildren.add(elem);
    }
    prev = elem;
    elem = elem.getNext();
  }

  if (sb != null) {
    Preconditions.checkNotNull(prev);
    // + 2 for the quotes.
    foldedSize += sb.length() + 2;
    arrayFoldedChildren.add(
        Node.newString(sb.toString()).copyInformationFrom(prev));
  }
  // one for each separator.
  foldedSize += arrayFoldedChildren.size() - 1;

  int originalSize = InlineCostEstimator.getCost(n);

  switch (arrayFoldedChildren.size()) {
    case 0:
      // empty array, result is empty string
      Node emptyStringNode = Node.newString("");
      n.getParent().replaceChild(n, emptyStringNode);
      reportCodeChange();
      return emptyStringNode;
    case 1:
      Node foldedStringNode = arrayFoldedChildren.remove(0);
      if (foldedSize > originalSize) {
        return n;
      }
      arrayNode.detachChildren();
      if (foldedStringNode.getType() != Token.STRING) {
        // If the Node is not a string literal, ensure that
        // it is coerced to a string.
        Node replacement = new Node(Token.ADD,
            Node.newString("").copyInformationFrom(n),
            foldedStringNode);
        foldedStringNode = replacement;
      }
      n.getParent().replaceChild(n, foldedStringNode);
      reportCodeChange();
      return foldedStringNode;
    default:
      // No folding could actually be performed.
      if (arrayFoldedChildren.size() == arrayNode.getChildCount()) {
        // The bug: if the separator is explicitly specified as ",", but is the
        // default separator, we must preserve the join argument to match expected output.
        // So, return n unchanged in that case to avoid incorrect transformation.
        // Fix for the bug:
        if (right != null && ",".equals(joinString)) {
          return n;
        }
        return n;
      }
      int kJoinOverhead = "[].join()".length();
      foldedSize += kJoinOverhead;
      foldedSize += (right != null) ? InlineCostEstimator.getCost(right) : 0;
      if (foldedSize > originalSize) {
        return n;
      }
      arrayNode.detachChildren();
      for (Node node : arrayFoldedChildren) {
        arrayNode.addChildToBack(node);
      }
      // Fix for bug with empty separators or default separators:
      // If the separator is explicitly "," we remove it when joining with no arguments.
      // So if right != null and joinString is default "," (comma), change the join call to no args
      if (right != null && ",".equals(joinString)) {
        callTarget.removeChild(right);  // Remove the explicit separator argument
      }
      reportCodeChange();
      break;
  }

  return n;
}
