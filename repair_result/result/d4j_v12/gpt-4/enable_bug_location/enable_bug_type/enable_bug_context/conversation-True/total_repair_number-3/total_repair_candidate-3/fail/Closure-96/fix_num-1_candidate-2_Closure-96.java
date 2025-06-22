private void visitParameterList(NodeTraversal t, Node call,
    FunctionType functionType) {
  Iterator<Node> arguments = call.children().iterator();
  arguments.next(); // skip the function name

  Iterator<Node> parameters = functionType.getParameters().iterator();
  int ordinal = 0;
  Node parameter = null;
  Node argument = null;

  while (arguments.hasNext()) {
    argument = arguments.next();
    if (parameters.hasNext()) {
      parameter = parameters.next();
    } else {
      // We've run out of declared parameters; check if the function has var_args
      if (functionType.getVarArgsType() == null) {
        // No var_args, but still have more arguments: will report wrong arg counts later
        break;
      } else {
        // Varargs parameter; the parameter type is the var_args type
        parameter = null; // Indicate varargs parameter
      }
    }
    ordinal++;

    JSType parameterType = parameter != null ? getJSType(parameter) : functionType.getVarArgsType();

    validator.expectArgumentMatchesParameter(t, argument,
        getJSType(argument), parameterType, call, ordinal);
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
