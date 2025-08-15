private void declareArguments(Node functionNode) {
  Node astParameters = functionNode.getFirstChild().getNext();
  Node body = astParameters.getNext();
  FunctionType functionType = (FunctionType) functionNode.getJSType();
  if (functionType != null) {
    Node jsDocParameters = functionType.getParametersNode();
    for (Node astParameter : astParameters.children()) {
      Node jsDocParameter = null;
      if (jsDocParameters != null) {
        jsDocParameter = jsDocParameters.getFirstChild();
      }
      // Advance the jsDocParameter node for the current astParameter
      for (Node param = jsDocParameters != null ? jsDocParameters.getFirstChild() : null;
           param != null && param != jsDocParameter;
           param = param.getNext()) {
        // no-op, just advance to the position matching astParameter
      }
      if (jsDocParameter != null) {
        defineSlot(astParameter, functionNode, jsDocParameter.getJSType(), true);
        jsDocParameters = jsDocParameters.getFirstChild() != null ? jsDocParameters.getNext() : null;
      } else {
        defineSlot(astParameter, functionNode, null, true);
      }
      if (jsDocParameters != null) {
        jsDocParameters = jsDocParameters.getNext();
      }
    }
  }
} // end declareArguments
