private void declareArguments(Node functionNode) {
  Node astParameters = functionNode.getFirstChild().getNext();
  Node body = astParameters.getNext();
  FunctionType functionType = (FunctionType) functionNode.getJSType();
  if (functionType != null) {
    Node jsDocParameters = functionType.getParametersNode();
    if (jsDocParameters != null) {
      Node jsDocParameter = jsDocParameters.getFirstChild();
      for (Node astParameter : astParameters.children()) {
        // Use getBestLValue and getBestJSDocInfo to get accurate type info for the parameter
        Node lValue = TypedScopeCreator.getBestLValue(astParameter);
        JSDocInfo jsDocInfo = TypedScopeCreator.getBestJSDocInfo(astParameter);
        JSType jsDocType = null;
        if (jsDocInfo != null && jsDocParameter != null) {
          jsDocType = jsDocParameter.getJSType();
        }
        if (lValue != null) {
          defineSlot(lValue, functionNode, jsDocType, true);
        } else {
          // fallback if lValue can't be determined
          defineSlot(astParameter, functionNode, jsDocType, true);
        }
        jsDocParameter = jsDocParameter != null ? jsDocParameter.getNext() : null;
      }
    }
  }
} // end declareArguments
