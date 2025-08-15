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

    // Ensure argument is of a valid type before validation
    JSType argumentType = getJSType(argument);
    JSType parameterType = getJSType(parameter);
    
    // Input validation for argument types
    if (argumentType == null || parameterType == null) {
      report(t, argument, INVALID_INTERFACE_MEMBER_DECLARATION,
          "Argument or parameter type is invalid");
      return;
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
