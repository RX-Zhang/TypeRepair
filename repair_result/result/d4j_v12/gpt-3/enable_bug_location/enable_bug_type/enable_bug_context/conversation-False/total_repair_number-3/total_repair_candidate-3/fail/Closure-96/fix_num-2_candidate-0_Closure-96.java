private void visitParameterList(NodeTraversal t, Node call,
    FunctionType functionType) {
  Iterator<Node> arguments = call.children().iterator();
  arguments.next(); // skip the function name

  Iterator<Node> parameters = functionType.getParameters().iterator();
  int ordinal = 0;
  Node parameter = null;
  Node argument = null;
  while (arguments.hasNext() &&
         parameters.hasNext()) {
    // If there are no parameters left in the list, then the while loop
    // above implies that this must be a var_args function.
    parameter = parameters.next();
    argument = arguments.next();
    ordinal++;

    // Ensure argument and parameter types are valid before matching
    JSType argumentType = getJSType(argument);
    JSType parameterType = getJSType(parameter);
    
    if (argumentType == null || parameterType == null) {
      // Handle the case where types cannot be determined
      report(t, call, WRONG_ARGUMENT_COUNT,
              validator.getReadableJSTypeName(call.getFirstChild(), false),
              String.valueOf(ordinal), String.valueOf(functionType.getMinArguments()),
              functionType.getMaxArguments() != Integer.MAX_VALUE ?
              " and no more than " + functionType.getMaxArguments() + " argument(s)" : "");
      return; // Exit early if types are invalid
    }

    validator.expectArgumentMatchesParameter(t, argument,
        argumentType, parameterType, call, ordinal);
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
