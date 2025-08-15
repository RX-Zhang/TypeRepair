private boolean hasExceptionHandler(Node cfgNode) {
  Node parent = cfgNode.getParent();
  while (parent != null) {
    if (parent.isTry()) {
      Node tryBlock = parent.getFirstChild();
      Node catchBlock = tryBlock.getNext();
      Node finallyBlock = catchBlock != null ? catchBlock.getNext() : null;
      // Check if cfgNode is inside try or catch block
      if (NodeUtil.isAncestor(tryBlock, cfgNode, true) || 
          (catchBlock != null && NodeUtil.isAncestor(catchBlock, cfgNode, true))) {
        return true;
      }
    }
    parent = parent.getParent();
  }
  return false;
}
