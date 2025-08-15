private boolean isOneExactlyFunctionOrDo(Node n) {
    int type = n.getType();
    if (type == Token.FUNCTION || type == Token.DO) {
      return true;
    }
    
    // Also allow labeled FUNCTION or DO statements,
    // i.e., when the node is a LABEL and its first child is FUNCTION or DO.
    if (type == Token.LABEL) {
      Node firstChild = n.getFirstChild();
      if (firstChild != null) {
        int childType = firstChild.getType();
        if (childType == Token.FUNCTION || childType == Token.DO) {
          return true;
        }
      }
    }
    
    return false;
}
