private void visitParameterList(NodeTraversal t, Node call,
    FunctionType functionType) {
  Iterator<Node> arguments = call.children().iterator();
  if (!arguments.hasNext()) {
      return; // Handle case with no arguments
  }
  arguments.next(); // skip the function name

  Iterator<Node> parameters = functionType.getParameters().iterator();
  int ordinal = 0;
  Node parameter = null;
  Node argument = null;
  while (arguments.hasNext() && parameters.hasNext()) {
    parameter = parameters.next();
    argument = arguments.next();
    ordinal++;

    // Type checking to avoid TypeError
    JSType argumentType = getJSType(argument);
    JSType parameterType = getJSType(parameter);
    if (argumentType == null || parameterType == null) {
        // Log a warning when types cannot be determined
        report(t, call, UNKNOWN_EXPR_TYPE, "Could not determine types for argument or parameter.");
        continue; // Skip type checking for this argument
    }

    validator.expectArgumentMatchesParameter(t, argument, argumentType, parameterType, call, ordinal);
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
