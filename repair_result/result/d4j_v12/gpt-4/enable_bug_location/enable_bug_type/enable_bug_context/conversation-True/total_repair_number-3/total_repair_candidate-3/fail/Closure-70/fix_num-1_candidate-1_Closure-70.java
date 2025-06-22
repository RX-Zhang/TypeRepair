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
        JSDocInfo jsDocInfo = getBestJSDocInfo(astParameter);
        JSType paramType = null;
        if (jsDocParameter != null) {
          paramType = jsDocParameter.getJSType();
        } else if (jsDocInfo != null) {
          paramType = jsDocInfo.getType();
        }
        defineSlot(lValue != null ? lValue : astParameter, functionNode, paramType, true);
        if (jsDocParameter != null) {
          jsDocParameter = jsDocParameter.getNext();
        }
      }
    }
  }
} // end declareArguments
