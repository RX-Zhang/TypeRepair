private void declareArguments(Node functionNode) {
  Node astParameters = functionNode.getFirstChild().getNext();
  Node body = astParameters.getNext();
  FunctionType functionType = (FunctionType) functionNode.getJSType();
  if (functionType != null) {
    Node jsDocParameters = functionType.getParametersNode();
    if (jsDocParameters != null) {
      Node jsDocParameter = jsDocParameters.getFirstChild();
      for (Node astParameter : astParameters.children()) {
        if (jsDocParameter != null) {
          // Use best JSDoc info to get the correct Type of parameter to avoid type mismatch bugs
          JSDocInfo jsDocInfo = TypedScopeCreator.getBestJSDocInfo(jsDocParameter);
          JSType jsDocType = jsDocInfo == null ? null : jsDocInfo.getType();
          defineSlot(astParameter, functionNode,
              jsDocParameter.getJSType() != null ? jsDocParameter.getJSType() : jsDocType, true);
          jsDocParameter = jsDocParameter.getNext();
        } else {
          defineSlot(astParameter, functionNode, null, true);
        }
      }
    }
  }
} // end declareArguments
