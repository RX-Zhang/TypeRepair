private void declareArguments(Node functionNode) {
  Node astParameters = functionNode.getFirstChild().getNext();
  Node body = astParameters.getNext();
  FunctionType functionType = (FunctionType) functionNode.getJSType();
  if (functionType != null) {
    Node jsDocParameters = functionType.getParametersNode();
    if (jsDocParameters != null) {
      Node jsDocParameter = jsDocParameters.getFirstChild();
      for (Node astParameter : astParameters.children()) {
        Node lValue = getBestLValue(astParameter);
        if (lValue != null) {
          JSDocInfo info = getBestJSDocInfo(astParameter);
          JSType jsDocType = (info != null) ? info.getType() : null;
          if (jsDocParameter != null) {
            defineSlot(lValue, functionNode, jsDocParameter.getJSType(), true);
            jsDocParameter = jsDocParameter.getNext();
          } else {
            defineSlot(lValue, functionNode, null, true);
          }
        } else {
          if (jsDocParameter != null) {
            defineSlot(astParameter, functionNode, jsDocParameter.getJSType(), true);
            jsDocParameter = jsDocParameter.getNext();
          } else {
            defineSlot(astParameter, functionNode, null, true);
          }
        }
      }
    }
  }
} // end declareArguments
