private void visitParameterList(NodeTraversal t, Node call,
    FunctionType functionType) {
  Iterator<Node> arguments = call.children().iterator();
  arguments.next(); // skip the function name

  Iterator<Node> parameters = functionType.getParameters().iterator();
  int ordinal = 0;
  Node parameter = null;
  Node argument = null;
  while (arguments.hasNext() &&
         (parameters.hasNext() || functionType.isConstructor() && functionType.isVarArgs())) {
    if (parameters.hasNext()) {
      parameter = parameters.next();
    } else {
      // For var_args functions, repeat the last parameter type.
      parameter = null;
    }
    argument = arguments.next();
    ordinal++;

    JSType paramType = parameter == null ? functionType.getVarArgsType() : getJSType(parameter);
    validator.expectArgumentMatchesParameter(t, argument,
        getJSType(argument), paramType, call, ordinal);
  }

  int numArgs = call.getChildCount() - 1;
  int minArgs = functionType.getMinArguments();
  int maxArgs = functionType.getMaxArguments();
  if (minArgs > numArgs || maxArgs < numArgs) {
    report(t, call, WRONG_ARGUMENT_COUNT,
            validator.getReadableJSTypeName(call.getFirstChild(), false),
            String.valueOf(numArgs), String.valueOf(minArgs),
            maxArgs != Integer.MAX_VALUE ?
            " and no more than " + maxArgs + " argument(s)" : "");
  }
}
