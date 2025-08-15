private void declareArguments(Node functionNode) {
  Node astParameters = functionNode.getFirstChild().getNext();
  Node body = astParameters.getNext();
  FunctionType functionType = (FunctionType) functionNode.getJSType();
  if (functionType != null) {
    Node jsDocParameters = functionType.getParametersNode();
    if (jsDocParameters != null) {
      Node jsDocParameter = jsDocParameters.getFirstChild();
      for (Node astParameter : astParameters.children()) {
        Node bestLValue = TypedScopeCreator.getBestLValue(astParameter);
        JSDocInfo jsDocInfo = TypedScopeCreator.getBestJSDocInfo(astParameter);
        if (jsDocParameter != null && jsDocInfo != null) {
          defineSlot(bestLValue, functionNode,
              jsDocParameter.getJSType(), true);
          jsDocParameter = jsDocParameter.getNext();
        } else {
          defineSlot(bestLValue != null ? bestLValue : astParameter, functionNode, null, true);
        }
      }
    }
  }
} // end declareArguments
