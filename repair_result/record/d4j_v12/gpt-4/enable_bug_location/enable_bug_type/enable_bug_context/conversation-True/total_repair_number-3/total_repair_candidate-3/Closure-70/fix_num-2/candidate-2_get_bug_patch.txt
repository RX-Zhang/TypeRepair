private void declareArguments(Node functionNode) {
  Node astParameters = functionNode.getFirstChild().getNext();
  Node body = astParameters.getNext();
  FunctionType functionType = (FunctionType) functionNode.getJSType();
  if (functionType != null) {
    for (Node astParameter : astParameters.children()) {
      JSDocInfo jsDocInfo = getBestJSDocInfo(astParameter);
      JSType jsDocType = jsDocInfo == null ? null : jsDocInfo.getType();
      defineSlot(astParameter, functionNode, jsDocType, true);
    }
  }
} // end declareArguments
